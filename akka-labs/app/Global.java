import play.Application;
import play.GlobalSettings;
//
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;


/**
 * Application wide behaviour. We establish a Spring application context for the dependency injection system and
 * configure Spring Data.
 */
public class Global extends GlobalSettings {

    // ########################################################################################################
    // ############################################## CONFIG TO ENABLE CORS ###################################

    private class ActionWrapper extends Action.Simple {
        public ActionWrapper(Action<?> action) {
            this.delegate = action;
        }

        @Override
        public F.Promise<Result> call(Http.Context ctx) throws java.lang.Throwable {
            F.Promise<Result> result = this.delegate.call(ctx);
            Http.Response response = ctx.response();
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
            return result;
        }
    }

    @Override
    public Action<?> onRequest(Http.Request request, java.lang.reflect.Method actionMethod) {
        return new ActionWrapper(super.onRequest(request, actionMethod));
    }
}
