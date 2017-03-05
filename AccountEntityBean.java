/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Account_Duy;

import Connection.ConnectDataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;

/**
 *
 * @author My
 */
public class AccountEntityBean implements EntityBean {
    String Username="";
    String Password="";
    String CodeRole="";

    public String getCodeRole() {
        return CodeRole;
    }

    public void setCodeRole(String CodeRole) {
        this.CodeRole = CodeRole;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public EntityContext getContext() {
        return context;
    }

    public void setContext(EntityContext context) {
        this.context = context;
    }


    private EntityContext context;
    
    // <editor-fold defaultstate="collapsed" desc="EJB infrastructure methods. Click the + sign on the left to edit the code.">

    // TODO Add code to acquire and use other enterprise resources (DataSource, JMS, enterprise beans, Web services)
    // TODO Add business methods
    // TODO Add create methods

    /**
     * @see javax.ejb.EntityBean#setEntityContext(javax.ejb.EntityContext)
     */
    public void setEntityContext(EntityContext aContext) {
        context = aContext;
    }
    
    /**
     * @see javax.ejb.EntityBean#ejbActivate()
     */
    public void ejbActivate() {
        
    }
    
    /**
     * @see javax.ejb.EntityBean#ejbPassivate()
     */
    public void ejbPassivate() {
        
    }
    
    /**
     * @see javax.ejb.EntityBean#ejbRemove()
     */
    public void ejbRemove() {
           try
        {
            Connection con=null;
            con=ConnectDataBase.getConnec();
            PreparedStatement ps=con.prepareCall("{call D_Account_Duy(?)}");
            ps.setString(1,getUsername());
            ps.executeUpdate();
            con.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    /**
     * @see javax.ejb.EntityBean#unsetEntityContext()
     */
    public void unsetEntityContext() {
        context = null;
    }
    
    /**
     * @see javax.ejb.EntityBean#ejbLoad()
     */
    public void ejbLoad() {
        // TODO add code to retrieve data
    }
    
    /**
     * @see javax.ejb.EntityBean#ejbStore()
     */
    public void ejbStore() {
        // TODO add code to persist data
        try
        {
            Connection con=null;
            con=ConnectDataBase.getConnec();
            PreparedStatement ps=con.prepareCall("{call U_Account_Duy(?,?)}");
            ps.setString(1,getUsername());
            ps.setString(2,getCodeRole());
            ps.executeUpdate();
            con.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    // </editor-fold>
    
    /**
     * See EJB 2.0 and EJB 2.1 section 12.2.5
     */
    public java.lang.String ejbFindByPrimaryKey(java.lang.String aKey) throws FinderException {
        // TODO add code to locate aKey from persistent storage
        // throw javax.ejb.ObjectNotFoundException if aKey is not in
        // persistent storage.
        setUsername(aKey);
        return aKey;
    }

    public String ejbCreate(String Username, String Password, String CodeRole) throws CreateException {
        String kq="";
        try
        {
            Connection con=null;
            con=ConnectDataBase.getConnec();
            PreparedStatement ps=con.prepareCall("{call I_Account_Duy(?,?,?)}");
            ps.setString(1,Username);
            ps.setString(2,Password);
            ps.setString(3,CodeRole);
            if(ps.executeUpdate()<=0)
            {
                kq="Username Same.Please Change Username";
            }else
            {
                setUsername(Username);
                setPassword(Password);
                setCodeRole(CodeRole);
                kq="Successful";
            }
            con.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return kq;
    }

    public void ejbPostCreate(String Username, String Password, String CodeRole) throws CreateException {
        
    }

}
