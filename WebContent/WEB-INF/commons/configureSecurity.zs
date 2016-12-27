transaccion = arg.get("transaccion");
List listaPermisos = desktop.getSession().getAttribute("permiso");
Component compoP = page.getFirstRoot();
List opers = servicioAdministrarConfiguracion.buscarOperacionesTodas();

if (listaPermisos != null)
{
	for(Operacion oper : opers)
	{
		boolean enc = false;
		for(Permiso permi : listaPermisos)
		{	
			//Component comp = compoP.getFellow(permi.getOperacion().getNombre());
			if (permi.getOperacion().getNombre().equals(oper.getNombre()))
			{
				enc = true;
				break;
			}
		}
		
		if (!enc)
		{
			//Button boton =compoP.query("window #" + oper.getNombre());
			//List listaBotones = compoP.queryAll(".seguridad");
			Iterator iter;
			if (oper.getNombre().equals("Incluir"))
			{
				iter = compoP.queryAll(".segIncl").iterator();
			}
			else if (oper.getNombre().equals("Editar"))
			{
				iter = compoP.queryAll(".segEdit").iterator();
			}
			else if (oper.getNombre().equals("Eliminar"))
			{
				iter = compoP.queryAll(".segElim").iterator();
			}
			else if (oper.getNombre().equals("Consultar"))
			{
				iter = compoP.queryAll(".segCons").iterator();
			}
			
			List listaBotones = new ArrayList();
			while (iter.hasNext())
			{
				listaBotones.add(iter.next());
			}
			
			for (Component botonEliminar : listaBotones)
			{
				botonEliminar.setVisible(false);
			}
		}
	}
}
