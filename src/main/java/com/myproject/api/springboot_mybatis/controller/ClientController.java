package  com.myproject.api.springboot_mybatis.controller;
import com.myproject.api.springboot_mybatis.entity.Client;
import com.myproject.api.springboot_mybatis.serviceImpl.ClientService;
import org.apache.ibatis.annotations.Param;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ClientController{

    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/getClientInfo/{client_id}",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String ReadClientInfo(@PathVariable int client_id) throws JSONException {
        Client client = clientService.readClient(client_id);
        JSONObject jsonObject = new JSONObject();
        String result;
        if(client!=null){
            jsonObject.put("data",client);
            result = "success";
        }else{
            result = "error";
            jsonObject.put("msg","No such client");
        }
        jsonObject.put("status",result);
        return jsonObject.toString();
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/readAllClient")
    public List<Map> ReadAllClient() throws JSONException {
        List<Client> clientList = clientService.readAllClient();
        List<Map> mapList = new ArrayList<>();
        for(Client c : clientList){
            Map<String,Object> resultMap  = new HashMap<String, Object>();
            resultMap.put("client_id",c.getClient_id());
            resultMap.put("client_name",c.getClient_name());
            resultMap.put("client_type",c.getClient_type());
            resultMap.put("client_work_address",c.getClient_work_address());
            resultMap.put("client_representative",c.getClient_representative());
            resultMap.put("client_business",c.getClient_business());
            resultMap.put("client_registered_capital",c.getClient_registered_capital());
            resultMap.put("client_person_name",c.getClient_person_name());
            resultMap.put("client_person_phone",c.getClient_person_phone());
            mapList.add(resultMap);
        }
        return mapList;
    }
    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/client/delete")
    public String DeleteClient(@Param("client_id") String client_id ) throws JSONException {
        int result =  clientService.DeleteClient(client_id);
        JSONObject object = new JSONObject();
        if(result == 1){
            object.put("status","success");
        }else{
            object.put("status","error");
        }
        return object.toString();
    }
    @CrossOrigin
    @ResponseBody
    @PostMapping(value="/client/update")
    public String UpdateClient(@Param("client_id") String client_id,@Param("client_name") String client_name,
                               @Param("client_work_address") String client_work_address,@Param("client_representative") String client_representative,
                               @Param("client_registered_capital") String client_registered_capital,@Param("client_type") String client_type,
                               @Param("client_business") String client_business,@Param("client_person_name") String client_person_name,
                               @Param("client_person_phone") String client_person_phone) throws JSONException {
        int result = clientService.UpdateClient(client_id,client_name,client_type,client_work_address,client_representative
        ,client_business,client_registered_capital,client_person_name,client_person_phone);
        JSONObject object = new JSONObject();
        if(result == 1){
            object.put("status","success");
        }else{
            object.put("status","error");
        }
        System.out.println(object.toString());
        return object.toString();
    }
    @CrossOrigin
    @ResponseBody
    @PostMapping(value="/client/add")
    public String InsertClient(@Param("client_id") String client_id,@Param("client_name") String client_name,
                               @Param("client_work_address") String client_work_address,@Param("client_representative") String client_representative,
                               @Param("client_registered_capital") String client_registered_capital,@Param("client_type") String client_type,
                               @Param("client_business") String client_business,@Param("client_person_name") String client_person_name,
                               @Param("client_person_phone") String client_person_phone) throws JSONException {
        Client client = new Client();
        client.setClient_id(client_id);
        client.setClient_name(client_name);
        client.setClient_work_address(client_work_address);
        client.setClient_representative(client_representative);
        client.setClient_registered_capital(client_registered_capital);
        client.setClient_type(client_type);
        client.setClient_business(client_business);
        client.setClient_person_name(client_person_name);
        client.setClient_person_phone(client_person_phone);
        int result = clientService.InsertClient(client);
        JSONObject object = new JSONObject();
        if(result==1){
            object.put("status","success");
        }else{
            object.put("status","error");
        }
        return object.toString();
    }


//    @RequestMapping(value="/updateClient/{id}/{changeField}/{newValue}")
//    public String updateClient(@PathVariable String id,@PathVariable String changeField,@PathVariable String newValue){
//        clientService.UpdateClient(id,changeField,newValue);
//        return id;
//    }

//    @ResponseBody
//    @GetMapping("/Apis/getWorksToShow")
//    public String worksToShow(HttpServletResponse response){
//        String msg = "{\"works\":[{\"name\":\"财务收支审计\"},{\"name\":\"企业清算审计\"},{\"name\":\"改制审计\"}]}";
////        JSONObject obj = JSON.parseObject("{\"runoob\":\"菜鸟教程\"}");
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Cache-Control","no-cache");
//        return msg;
//    }


}

