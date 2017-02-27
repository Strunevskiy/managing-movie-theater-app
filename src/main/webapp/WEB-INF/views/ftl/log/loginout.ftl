<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Login page</title>
        <link href="/static/css/bootstrap.css"  rel="stylesheet"></link>
        <link href="/static/css/app.css" rel="stylesheet"></link>
        <link rel="stylesheet" type="text/css" href=http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.css">        
    </head>
 
    <body>
        <div id="mainWrapper">
            <div class="login-container">
                <div class="login-card">
                    <div class="login-form">
                        <form action="/login" method="post" class="form-horizontal">
                        
                        <#if RequestParameters.error??>
                          <div class="alert alert-danger">
                             <p>Invalid username and password.</p>
                           </div>
                        </#if>
                        
                        <#if RequestParameters.logout??>
                          <div class="alert alert-success">
                             <p>You have been logged out successfully.</p>
                           </div>
                        </#if>

                                                      
                            <div class="input-group input-sm">
                                <label class="input-group-addon" for="username"><i class="fa fa-user"></i></label>
                                <input type="text" class="form-control" id="username" name="email" placeholder="Enter email" required>
                            </div>
                            <div class="input-group input-sm">
                                <label class="input-group-addon" for="password"><i class="fa fa-lock"></i></label> 
                                <input type="password" class="form-control" id="password" name="password" placeholder="Enter Password" required>
                            </div>
                            <div class="input-group input-sm">
                              <div class="checkbox">
                                <label><input type="checkbox" id="rememberme" name="remember-me"> Remember Me</label>  
                              </div>
                            </div>                                 
                            <div class="form-actions">
                                <input type="submit"
                                    class="btn btn-block btn-primary btn-default" value="Log in">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
 
    </body>
</html>
