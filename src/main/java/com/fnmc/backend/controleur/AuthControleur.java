package com.fnmc.backend.controleur;

import com.fnmc.backend.entity.Comps;
import com.fnmc.backend.service.StructService;
import com.fnmc.backend.service.UserMcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


@RestController
@RequestMapping("auth")
@CrossOrigin("http://localhost:3000")
public class AuthControleur {
    @Autowired
    UserMcService userMcService;

    @Autowired
    StructService structService;

    @PostMapping("loginServer")
  //  @CrossOrigin(origins = "http://localhost:2555")
    public ResponseEntity loginR(@RequestBody Map<String, String> request) {
        String nom = request.get("name");
        String password = request.get("password");
        String res = userMcService.loginServer(nom,password);

       if (res==null) {
           return new ResponseEntity("ID, ou Mot de passe incorrect", HttpStatus.UNAUTHORIZED);
       }

           return ResponseEntity.ok(res);

    }

    @GetMapping("test")
    public ResponseEntity test() {
        return  ResponseEntity.ok("ok");
    }

    @PostMapping("login")
    public ResponseEntity loginE(@RequestBody Map<String, String> request) {
        String nom = request.get("name");
        String password = request.get("password");
        String id = request.get("id");
        try {
        Map<String,Object > res = userMcService.login(nom,password,id);
        if (res==null) {
            return new ResponseEntity("ID, ou Mot de passe incorrect", HttpStatus.UNAUTHORIZED);}
        return ResponseEntity.ok(res);}
        catch (Exception e){
            return new ResponseEntity<>("une erreur c'est produite",HttpStatus.INTERNAL_SERVER_ERROR);}
    }

    @PostMapping("isLogin")
  //  @CrossOrigin(origins = "https://localhost:5173")
    public ResponseEntity isLogin(@RequestBody Map<String, String> request) {
        String nom = request.get("name");
        String password = request.get("password");
        //String id = request.get("id");
        String res = userMcService.loginServer(nom,password);
        if(res!="")
            return ResponseEntity.ok("it ok to next");


            return new ResponseEntity("ID, ou Mot de passe incorrect", HttpStatus.UNAUTHORIZED);




    }





    @GetMapping("get3/{n}")
   public ResponseEntity geting3(@PathVariable String n) {
        System.out.println("ping3");

        //todo mélange  donc go écrasé les data mais ya tjr le ..[2] sous same
        try {
            Comps op = structService.getuniqueC("tds_lpa", n);//.GreyCactus67220
            if (op == null) {
                //System.out.println(op.getId());
                return ResponseEntity.ofNullable("not found user/struct");
            }
            return ResponseEntity.ok(op);

        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.ok("non sa bug en fait"+e);
        }

    }


        /*
            Comps c =structService.getingHeal(id,n);
            System.out.println(c);
            System.out.println("is changing");
            if(c!=null)
                return ResponseEntity.ok(c);*/



/*    @GetMapping("get0/{n}")
    public ResponseEntity geting0(@PathVariable String n){
        System.out.println("ping0");
        Comps op=structService.geting(n);//.GreyCactus67220
        if (op==null)
            return ResponseEntity.ofNullable("not found user/struct");
        return ResponseEntity.ok(op);
    }*/
}

