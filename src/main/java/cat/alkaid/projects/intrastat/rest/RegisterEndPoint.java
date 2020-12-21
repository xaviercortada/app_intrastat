package cat.alkaid.projects.intrastat.rest;

import javax.ws.rs.POST;
import javax.annotation.security.PermitAll;
import cat.alkaid.projects.intrastat.model.Account;
import cat.alkaid.projects.intrastat.util.MessageBuilder;
import javax.ws.rs.core.Response;
import cat.alkaid.projects.intrastat.model.RegisterDto;
import javax.enterprise.inject.Any;
import cat.alkaid.projects.intrastat.model.Email;
import javax.enterprise.event.Event;
import cat.alkaid.projects.intrastat.service.RegisterService;
import javax.inject.Inject;
import cat.alkaid.projects.intrastat.service.AccountService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;

@RequestScoped
@Path("/register")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class RegisterEndPoint
{
    @Inject
    private AccountService accountService;
    @Inject
    private RegisterService registerService;
    @Inject
    @Any
    private Event<Email> event;
    
    @PermitAll
    @POST
    @Produces({ "application/json" })
    public Response createMember(final RegisterDto request) {
        if (!request.getPassword().equals(request.getPasswordConfirmation())) {
            return MessageBuilder.badRequest().message(new String[] { "Password mismatch." }).build();
        }
        MessageBuilder message;
        try {
            if (this.accountService.findByUsername(request.getUserName()) == null) {
                final Account newUser = this.registerService.createAccount(request);
                final String activationCode = newUser.getActivationCode();
                this.sendNotification(request, activationCode);
                message = MessageBuilder.ok().activationCode(activationCode);
            }
            else {
                message = MessageBuilder.badRequest().message(new String[] { "This username is already in use. Try another one." });
            }
        }
        catch (Exception e) {
            message = MessageBuilder.badRequest().message(new String[] { e.getMessage() });
        }
        return message.build();
    }
    
    @PermitAll
    @POST
    @Path("/activation")
    @Consumes({ "text/plain" })
    @Produces({ "application/json" })
    public Response activateAccount(final String activationCode) {
        final Account account = this.accountService.activateAccount(activationCode);
        if (account != null) {
            MessageBuilder message;
            try {
                message = MessageBuilder.ok().token("");
            }
            catch (Exception e) {
                message = MessageBuilder.badRequest().message(new String[] { e.getMessage() });
            }
            return message.build();
        }
        return null;
    }
    
    private void sendNotification(final RegisterDto request, final String activationCode) {
        final Email email = new Email("Please complete the signup", "http://appintrastat-xaviercortada.rhcloud.com/#/activate/" + activationCode, new String[] { request.getEmail() });
        this.event.fire((Object)email);
    }
}

