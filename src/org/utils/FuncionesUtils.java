package org.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.criterion.Restrictions;
import org.sevenlabs.exception.SevenLabsException;
import org.sevenlabs.modelo.dao.IHibernateDAO;
import org.zkoss.zul.Listitem;


public class FuncionesUtils
{	   
    public static void llenarSetListitem(Set set, List<Listitem> lista) {
 
        for (Listitem li : lista)
        {
        	set.add(li.getValue());
        }
    }
  
}
