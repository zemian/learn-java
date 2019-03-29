import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IJobHistory } from '@/shared/model/job-history.model';

const baseApiUrl = 'api/job-histories';

export default class JobHistoryService {
  public find(id): Promise<IJobHistory> {
    return new Promise<IJobHistory>(resolve => {
      axios.get(`${baseApiUrl}/${id}`).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public delete(id): Promise<any> {
    return new Promise<any>(resolve => {
      axios.delete(`${baseApiUrl}/${id}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public create(entity): Promise<IJobHistory> {
    return new Promise<IJobHistory>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity): Promise<IJobHistory> {
    return new Promise<IJobHistory>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
