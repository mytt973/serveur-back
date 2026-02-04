package com.fnmc.backend.service;

import com.fnmc.backend.entity.UserMc;
import com.fnmc.backend.enums.RoleEnum;
import com.fnmc.backend.repository.UserMcRepository;
import com.fnmc.backend.security.JwtService;
import com.fnmc.backend.service.iservice.IUserMcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserMcService implements IUserMcService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserMcRepository userMcRepository;


    @Override
    public UserMc createUser(UserMc user) {
        System.out.println(user + " oklmll");
        if (userMcRepository.findItemByName(user.getName()) != null) {
            return null;
        }


        try {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setHash(encodedPassword);
            List<RoleEnum> role = new ArrayList<>();
            role.add(RoleEnum.PLAYER);
            UserMc newUser = new UserMc(user.getName(), user.getHash(), role);
            user.setAsso("");
            userMcRepository.save(newUser);


        } catch (Exception e) {
            System.out.println(e);
        }
        return user;
    }

    public boolean removeOne(String user,Integer id){
        UserMc userMc=this.getUserByName(user);
        if (userMc!=null){
            List l = userMc.getStructIn();
            l.remove(l.get(id));
            userMc.setStructIn(l);
            userMcRepository.save(userMc);
            return true;
        }
        return false;
    }

    public boolean  updateStrucIn(String user,String id,String newV) {
        UserMc userMc = userMcRepository.findItemByName(user);

        if (userMc.getStructIn()!=null){
            for(Map e:userMc.getStructIn()){
                if (newV.equals(e.get("title"))) newV+="..x2g";//todo test
            }
            List<Map<String,String>> map=userMc.getStructIn();
            map.get(Integer.parseInt(id)).replace("title",newV);
            userMc.setStructIn(map);
            userMcRepository.save(userMc);
            return true;
        }
        return false;
    }


    public UserMc getUserByName(String named) {
        return userMcRepository.findItemByName(named);

    }


    @Override
    public String loginServer(String nom, String password) {
        UserMc user = this.getUserByName(nom);
        System.out.println(password + " : " + user.getHash());


        /*if (user == null || !passwordEncoder.matches(password, user.getHash())) {
            return null;
        }*/

       /* if (user.getAsso()!="") {
            user.setAsso(id);
        } else {
            System.out.println(user.getAsso()!=id);

        }*/
        // if (user.getAsso()!=null)
        //   return "";

        String jwt = jwtService.generateToken(user);
        System.out.println(jwt);
        return jwt;
    }

    @Override
    public Map login(String nom, String password, String id) {
        UserMc user = this.getUserByName(nom);
        System.out.println(password + " : " + user.getHash());

        if (password.equals(user.getHash())) {
            System.out.println("on site");

        } else {
            if (user == null || !passwordEncoder.matches(password, user.getHash())) {
                return null;
            }
        }
        if (user.getAsso() == "") {
            user.setAsso(id);
        } else {
            System.out.println(user.getAsso() != id);

        }
        String jwt = jwtService.generateToken(user);
        // System.out.println(jwt);
        Map map = new HashMap<>();
        //List<Map<String,String>> mapi=new ArrayList<>();
        map.put("jwt", jwt);
        map.put("map", user.getStructIn());
        return map;

    }
    //67b5ba2d1bedb31f7e05cb83
    //67b5be791bedb31f7e05cb84

    @Override
    public Optional<UserMc> getUser(String id) {
        Optional<UserMc> mc = userMcRepository.findById(id);
        if (mc.isEmpty())
            return Optional.empty();
        return mc;
    }

    @Override
    public void addStr(String name, String str, String id) {
        UserMc user = this.getUserByName(name);
        if (user != null) {
            System.out.println(user.getName());
            Map k = new HashMap<>();
            k.put("title", str);
            k.put("id", id);
            user.getStructIn().add(k);
            userMcRepository.save(user);
        }
    }


    public void reOrgenize(UserMc u) {
//todo replace " " -> ".."
        Integer i = 0;
        List<Map<String, String>> lst = u.getStructIn();
        Map<Integer, String> opt = new HashMap<>();

        for (Map<String, String> elem : lst) {
            String titre = elem.get("title");
            int nb = 0;
            for (Map<String, String> element : lst) {
                if (element.get("title").equals(titre)) {
                    nb = nb + 1;
                }
                if (element.get("id").equals(elem.get("id"))) {
                    break;
                }
            }
            if (nb != 1) {
                opt.put(i, elem.get("title") + "..x" + nb);
                System.out.println(i + "tezst " + elem);
            }
            i++;
        }

        opt.forEach((k, v) -> {
            lst.get(k).replace("title", v);
        });
//        System.out.println(opt);
        //System.out.println(lst);
        u.setStructIn(lst);
        userMcRepository.save(u);

    }

    @Override
    public Optional<List<String>> getAllUser() {
        List<UserMc> list = userMcRepository.findAllUserNames();
        System.out.println(list + "usermcService : L84");

        List<String> sLst = new ArrayList<>();
        for (UserMc user : list) {
            sLst.add(user.getName());
        }
        return Optional.of(sLst);
    }

}
