package com.zemian.hellojava.data.support;

import com.zemian.hellojava.data.dao.Paging;
import com.zemian.hellojava.data.dao.PagingList;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * You can use this class along with spring JdbcTemplate.query(sql, extractor, params) to support generic
 * pagination for DB that does not support OFFSET and LIMIT query feature.
 *
 * This is a poorman implementation of data pagination in a generic way. We waste all the data traffic by fetching the
 * offset rows as well, but just to throw it away! A better solution is to design the data table to have a integer
 * column that can be use as OFFSET in where clause inside the query. But unfortunately not all existing table
 * has this column. In this case we will just loop through the ResultSet cursor as if we "skip" to the OFFSET.
 *
 * NOTE: The caller must ensure SQL is sorted in a consistent fields between each paging call!
 *
 * NOTE2: To be somewhat efficient, caller may set the JDBC's ResultSet#setMaxRows() to be no more than OFFSET +
 * SIZE value.
 *
 * @Author Zemian Deng 2017
 */
public class PagingResultSetExtractor<T> implements ResultSetExtractor<PagingList<T>> {
    private RowMapper<T> rowMapper;
    private Paging paging;

    public PagingResultSetExtractor(RowMapper<T> rowMapper, Paging paging) {
        this.rowMapper = rowMapper;
        this.paging = paging;
    }

    @Override
    public PagingList<T> extractData(ResultSet rs) throws SQLException, DataAccessException {
        PagingList<T> result = new PagingList<>();
        int index = 0, offset = paging.getOffset(), size = paging.getSize();

        // Fast-forward to offset row first.
        // NOTE: Sybase does not support ResultSet.absolute()!
        while(index < offset && rs.next()) {
            // Do nothing but fast-forward the resultset!
            index++;
        }

        // Fetch size number of rows
        int count = 0;
        while (rs.next()) {
            if (count >= size) {
                // The fact we have reached size and inside this loop means we have more data!
                result.setMore(true);
                break;
            }

            // Let's extract the result set.
            T entity = rowMapper.mapRow(rs, index);
            result.getList().add(entity);
            count ++;
        }

        return result;
    }
}
