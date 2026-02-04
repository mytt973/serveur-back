package com.fnmc.backend.service;

import com.fnmc.backend.dao.CompsDao;
import com.fnmc.backend.entity.Comps;
import com.fnmc.backend.entity.UserMc;
import com.fnmc.backend.repository.CompRepo;
import com.fnmc.backend.service.iservice.IStructSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Optional;

@Service
public class StructService implements IStructSevice {


    @Autowired
    CompRepo compRepo;

    @Autowired
    UserMcService userMcService;



    private String getIdByNamed(String pl, String n) {
        UserMc u = userMcService.getUserByName(pl);
        for (Map<String, String> e : u.getStructIn()) {
            if (e.get("title").equals(n)) {
                return e.get("id");
            }
        }
        return null;
    }

    public Comps getuniqueC(String pl, String n) {
        String id = this.getIdByNamed(pl, n);
        System.out.println(id);
        Optional<Comps> c = compRepo.findById(id);
        if (c.isPresent()) {
            return c.get();
        }
        return null;
    }




    public Optional<Comps> updateStruct(CompsDao struct) {
        //todo use un GetId
        Comps str = new Comps();

        try {
             str.setBlst(struct.getBlst());
            str.setDtxt(struct.getDtxt());
            str.setNamed(struct.getNamed());
            str.setPlayer(struct.getPlayer());
            str.setCompData(struct.getCompData());
            str.setCompTxt(struct.getCompTxt());
            compRepo.save(str);
        } catch (Exception e) {
            System.out.println(e);
            return Optional.empty();
        }
        System.out.println(str);
        return Optional.of(str);
    }


    public boolean removeComp(String user, String n) {
        String id = getIdByNamed(user, n);
        if (id == null || id.isEmpty()) return false;
        Optional<Comps> comps = compRepo.findById(id);
        if (comps.isEmpty()) return false;
       // if (!comps.get().getPlayer().equals(user)) return false;
        compRepo.delete(comps.get());
        return true;
    }

    public Comps Publich(String user, String id) {
        Comps comps = compRepo.getStructByName(user, id);
        if (!comps.getPlayer().equals(user)) return null;
        comps.setIsPrivate(true);
        return compRepo.save(comps);
    }

    public Comps rePrivate(String user, String id) {
        Comps comps = compRepo.getStructByName(user, id);
        comps.setIsPrivate(false);
        return compRepo.save(comps);
    }


    public boolean renameComp(String user, String n, String newVal) {
        String id = this.getIdByNamed(user, n);
        Optional<Comps> comps = compRepo.findById(id);
        if (comps.isEmpty()) return false;
        if (!comps.get().getPlayer().equals(user)) return false;
        comps.get().setNamed(newVal);
        compRepo.save(comps.get());
        return true;
    }


}
