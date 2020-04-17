package com.how2java;

import java.util.ArrayList;
import java.util.List;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class TestShiro {
	public static void main(String[] args) {

        //����Ҫ�ͷ�ע�ͣ���ע��һ���û�
      //new DAO().createUser("tom", "123");
		
		List<User> users = new ArrayList<>();
		// �û���
		User zhang3 = new User();
		zhang3.setName("zhang3");
		zhang3.setPassword("12345");

		users.add(zhang3);

		User li4 = new User();
		li4.setName("li4");
		li4.setPassword("abcde");
		users.add(li4);

		User wang5 = new User();
		wang5.setName("wang5");
		wang5.setPassword("wrongpassword");
		users.add(wang5);

		// ��ɫ��
		String roleAdmin = "admin";
		String roleProductManager = "productManager";

		List<String> roles = new ArrayList<>();
		roles.add(roleAdmin);
		roles.add(roleProductManager);

		// Ȩ����
		String permitAddProduct = "addProduct";
		String permitAddOrder = "addOrder";

		List<String> permits = new ArrayList<>();
		permits.add(permitAddProduct);
		permits.add(permitAddOrder);

		// ��½ÿ���û�
		for (User user : users) {
			if (login(user))
				System.out.printf("%s \t�ɹ���½���õ������� %s\t %n", user.getName(), user.getPassword());
			else
				System.out.printf("%s \t�ɹ�ʧ�ܣ��õ������� %s\t %n", user.getName(), user.getPassword());
		}

		System.out.println("-------how2j �ָ���------");

		// �ж��ܹ���¼���û��Ƿ�ӵ��ĳ����ɫ
		for (User user : users) {
			for (String role : roles) {
				if (login(user)) {
					if (hasRole(user, role))
						System.out.printf("%s\t ӵ�н�ɫ: %s\t%n", user.getName(), role);
					else
						System.out.printf("%s\t ��ӵ�н�ɫ: %s\t%n", user.getName(), role);
				}
			}
		}
		System.out.println("-------how2j �ָ���------");

		// �ж��ܹ���¼���û����Ƿ�ӵ��ĳ��Ȩ��
		for (User user : users) {
			for (String permit : permits) {
				if (login(user)) {
					if (isPermitted(user, permit))
						System.out.printf("%s\t ӵ��Ȩ��: %s\t%n", user.getName(), permit);
					else
						System.out.printf("%s\t ��ӵ��Ȩ��: %s\t%n", user.getName(), permit);
				}
			}
		}

		System.out.printf("�������");
	}

	// �ж��ܹ���¼���û��Ƿ�ӵ��ĳ����ɫ
	private static boolean hasRole(User user, String role) {
		Subject subject = getSubject(user);
		return subject.hasRole(role);
	}

	// �ж��ܹ���¼���û����Ƿ�ӵ��ĳ��Ȩ��
	private static boolean isPermitted(User user, String permit) {
		Subject subject = getSubject(user);
		return subject.isPermitted(permit);
	}

	private static Subject getSubject(User user) {
		// ���������ļ�������ȡ����
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		// ��ȡ��ȫ������ʵ��
		SecurityManager sm = factory.getInstance();
		// ����ȫ�����߷���ȫ�ֶ���
		SecurityUtils.setSecurityManager(sm);
		// ȫ�ֶ���ͨ����ȫ����������Subject����
		Subject subject = SecurityUtils.getSubject();

		return subject;
	}

	// ��¼
	private static boolean login(User user) {
		Subject subject = getSubject(user);
		// ����Ѿ���¼���ˣ��˳�
		if (subject.isAuthenticated())
			subject.logout();

		// ��װ�û�������
		UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPassword());
		try {
			// ���û�������token ���մ��ݵ�Realm�н��жԱ�
			subject.login(token);
		} catch (AuthenticationException e) {
			// ��֤����
			return false;
		}

		return subject.isAuthenticated();
	}
}
