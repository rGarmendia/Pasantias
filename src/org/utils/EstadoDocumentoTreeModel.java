package org.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.AbstractTreeModel;
import org.zkoss.zul.ext.TreeSelectableModel;

public class EstadoDocumentoTreeModel<T> extends AbstractTreeModel<T> implements TreeSelectableModel {

	private Map valores = new HashMap();
	 
	public EstadoDocumentoTreeModel(Map valores) {
        super((T) "root");
        this.valores = valores;
    }

	@Override
	public boolean isLeaf(T paramE) {
		return (getChildCount(paramE) == 0);
	}

	@Override
	public T getChild(T paramE, int paramInt) {
		return (T) ((List)valores.get(paramE)) == null ? null : (T) ((List)valores.get(paramE)).get(paramInt);
	}

	@Override
	public int getChildCount(T paramE) {
		// TODO Auto-generated method stub
		return ((List)valores.get(paramE)) == null ? 0 : ((List)valores.get(paramE)).size();
	}

}
