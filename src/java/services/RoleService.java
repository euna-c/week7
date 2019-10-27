/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.RoleDB;
import models.User;
import java.util.List;
import dataaccess.UserDB;
import java.util.ArrayList;
import models.Role;

/**
 *
 * @author 784564
 */
public class RoleService {
    
     public List<Role> getAll() throws Exception {
        RoleDB db = new RoleDB();
        ArrayList<Role> activeRole = (ArrayList<Role>) db.getAll();
       
        return activeRole;
    }
     
    public int update(int roleID, String rolename) throws Exception {
        RoleDB db = new RoleDB();
        Role role = new Role(roleID, rolename);
        int i = db.update(role);
        return i;
    }

        public Role get(int roleID) throws Exception {
        RoleDB db = new RoleDB();
        Role role = db.getRole(roleID);
        return role;
    }
   
    public int delete(int roleID) throws Exception {
        RoleDB db = new RoleDB();
        Role role = get(roleID);
        //check
        int i = db.update(role);
        return i;
    }

    
    public int insert(int roleID, String rolename) throws Exception {
        RoleDB db = new RoleDB();
        Role role = new Role(roleID, rolename);
        int i = db.insert(role);
        return i;
    }

    
}
