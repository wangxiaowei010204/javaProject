package com.how2java;
 
import java.util.Set;
 
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
 
public class DatabaseRealm extends AuthorizingRealm {
 
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //�ܽ��뵽�����ʾ�˺��Ѿ�ͨ����֤��
        String userName =(String) principalCollection.getPrimaryPrincipal();
        //ͨ��DAO��ȡ��ɫ��Ȩ��
        Set<String> permissions = new DAO().listPermissions(userName);
        Set<String> roles = new DAO().listRoles(userName);
         
        //��Ȩ����
        SimpleAuthorizationInfo s = new SimpleAuthorizationInfo();
        //��ͨ��DAO��ȡ���Ľ�ɫ��Ȩ�޷Ž�ȥ
        s.setStringPermissions(permissions);
        s.setRoles(roles);
        return s;
    }
 
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //��ȡ�˺�����
        UsernamePasswordToken t = (UsernamePasswordToken) token;
        String userName= token.getPrincipal().toString();
        String password= new String( t.getPassword());
        //��ȡ���ݿ��е�����
        String passwordInDB = new DAO().getPassword(userName);
 
        //���Ϊ�վ����˺Ų����ڣ��������ͬ����������󣬵��Ƕ��׳�AuthenticationException���������׳��������ԭ����ø��ƽ����ṩ������Ϣ
        if(null==passwordInDB || !passwordInDB.equals(password))
            throw new AuthenticationException();
         
        //��֤��Ϣ�����˺�����, getName() �ǵ�ǰRealm�ļ̳з���,ͨ�����ص�ǰ���� :databaseRealm
        SimpleAuthenticationInfo a = new SimpleAuthenticationInfo(userName,password,getName());
        return a;
    }
 
}