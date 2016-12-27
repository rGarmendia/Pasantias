
import org.modelo.dto.pk.*;
import org.modelo.dto.*;
import org.modelo.dto.utils.*;
import org.modelo.servicios.*;
import org.sevenlabs.comunes.*;
import org.sevenlabs.vista.zk.command.*;
import org.zkoss.zk.ui.event.*;
import org.zkoss.zk.ui.Session;
import org.springframework.context.ApplicationContext;
import javax.servlet.ServletContext;
import org.zkoss.zkplus.spring.*;
import org.zkoss.util.media.*;
import org.zkoss.image.*;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;
import java.util.*;
import java.text.*;


List rolInvestigadores = new ArrayList();
rolInvestigadores.add("INVESTIGADOR");
rolInvestigadores.add("COORDINADOR GENERAL");
rolInvestigadores.add("COORDINADOR DE LINEA");
rolInvestigadores.add("COORDINADOR DE UNIDAD");
rolInvestigadores.add("COMISION DE REGISTRO");

org.sevenlabs.comunes.Contexto obeCtx = (org.sevenlabs.comunes.Contexto) desktop.getSession().getAttribute(org.sevenlabs.comunes.Contexto.ID_CONTEXTO);

Usuario usuario = app.getGuestUser();

if (obeCtx != null) {
	usuario = obeCtx.getUsuarioActual();
}  else {

			obeCtx = new ContextoImpl();

			Usuario usuario = app.getGuestUser();;
			obeCtx.setUsuarioActual(usuario);
			
			
			desktop.getSession().setAttribute(Contexto.ID_CONTEXTO, obeCtx);

}

