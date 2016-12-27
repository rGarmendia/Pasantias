<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
		%>
		<%@include file="/commons/taglibs.jsp"%>
		<title><decorator:title
				default="ISODocument" /></title>
		<meta http-equiv="content-type"
			content="text/html; charset=iso-8859-1" />
		<meta name="description" content="" />
		<meta name="keywords" content="" />
		<meta name="author" content="7Labs" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
		<link href='css/venta.css' rel='stylesheet' type='text/css' />
		<decorator:head />
	</head>


	<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0"
		marginwidth="0" marginheight="0">
		
		<table border="0" width="95%" align="center" cellpadding="0"
			cellspacing="0">

			<tr>
				<td colspan="3">
					<table border="0" width="100%" align="center" cellpadding="0"
						cellspacing="0">
			
						<tr>
							<td width="150px">
								<img src="images/logoISODocument.jpg" width="300px" />
							
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="100%" bgcolor="#ffffff" valign="top" colspan="3">
					<decorator:body />
				</td>
			</tr>
		</table>
	</body>
</html>
