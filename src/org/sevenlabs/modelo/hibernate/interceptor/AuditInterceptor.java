package org.sevenlabs.modelo.hibernate.interceptor;

import java.io.Serializable;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.modelo.dto.DocumentoAprobador;
import org.modelo.dto.DocumentoAprobadorPK;

































public class AuditInterceptor
  extends EmptyInterceptor
{
  public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
  {
    if ((entity instanceof DocumentoAprobador))
    {
    	DocumentoAprobador da = (DocumentoAprobador)entity;
    	DocumentoAprobadorPK pk = (DocumentoAprobadorPK)id;
      pk.setAprobadorid(da.getAprobador().getLogin());
      pk.setDocumentoid(da.getDocumento().getIdCodigo());
      
      da.setAprobadorid(da.getAprobador() != null ? da.getAprobador().getLogin() : null);
      da.setDocumentoid(da.getDocumento() != null ? da.getDocumento().getIdCodigo() : null);
    }
    return false;
  }
}
