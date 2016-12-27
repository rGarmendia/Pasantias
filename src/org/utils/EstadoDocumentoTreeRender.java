package org.utils;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

public class EstadoDocumentoTreeRender<T> implements TreeitemRenderer<T>{

	@Override
	public void render(Treeitem treeItem, T paramT, int paramInt) throws Exception {
		Treerow dataRow = new Treerow();
        dataRow.setParent(treeItem);
        treeItem.setValue(paramT);
        treeItem.setOpen(false);
        Treecell treeCell = new Treecell();
        treeCell.setStyle("font-size:20px");
        if (paramT instanceof String)
        {
        	treeCell.setLabel((String)paramT);
        	dataRow.appendChild(treeCell);
        }
        else
        {
        	org.modelo.dto.Documento doc = (org.modelo.dto.Documento) paramT;
        	Hlayout hl = new Hlayout();
        	hl.setStyle("font-size:20px; text-align: center;");
        	hl.setValign("center");
             hl.appendChild(new Image("/images/text.png"));
             Label lblDoc = new Label("("+doc.getCodigo()+") " + doc.getTitulo());
             lblDoc.setStyle("font-size:20px;");
             hl.appendChild(lblDoc);
             hl.setSclass("h-inline-block");
             treeCell.appendChild(hl);
             dataRow.appendChild(treeCell);
             dataRow.addEventListener(Events.ON_DOUBLE_CLICK, new org.zkoss.zk.ui.event.EventListener<Event>() {
            	 
				@Override
				public void onEvent(Event arg0) throws Exception {
					// TODO Auto-generated method stub
					
				}
             });
        }		
	}

}
