package edu.nju.software.util;

import java.util.Enumeration;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

public class CoHttpSessionAttributeListener implements HttpSessionAttributeListener {

	@Override
	public void attributeAdded(HttpSessionBindingEvent e) {
		// TODO Auto-generated method stub
		System.out.println("source: " + e.getSource());
		System.out.println("name : " + e.getName());
		System.out.println("value : " + e.getValue());
		System.out.println("attribute add, current attributes: ");
		Enumeration<String> names = e.getSession().getAttributeNames();
		while(names.hasMoreElements()) {
			System.out.println(names.nextElement());
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent e) {
		// TODO Auto-generated method stub
		System.out.println("source: " + e.getSource());
		System.out.println("name : " + e.getName());
		System.out.println("value : " + e.getValue());
		System.out.println("attribute removed, current attributes: ");
		Enumeration<String> names = e.getSession().getAttributeNames();
		while(names.hasMoreElements()) {
			System.out.println(names.nextElement());
		}
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent e) {
		// TODO Auto-generated method stub
		System.out.println("attribute replaced, current attributes: " + e.getSession().getAttributeNames());
	}

}
