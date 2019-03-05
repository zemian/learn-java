package zemian.myspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zemian.myspringboot.MyDatabase.Table;

import java.util.List;

@Controller
public class DbInfoController {
    @Autowired
    private MyDatabase myDatabase;

    @RequestMapping("/dbinfo/tables")
    @ResponseBody
    List<Table> dbinfo() {
        return myDatabase.listTables();
    }
}
