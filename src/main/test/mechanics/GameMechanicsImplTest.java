package mechanics;

import base.GameMechanics;
import base.GameUser;
import base.WebSocketService;
import database.DBService;
import database.DataBaseSettings;
import frontend.WebSocketServiceImpl;
import main.UserProfile;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.jws.soap.SOAPBinding;

import static org.mockito.Mockito.*;

/**
 * Created by nigga on 28.10.15.
 */
public class GameMechanicsImplTest {
    @NotNull
    private final DBService dbService = new DBService(new DataBaseSettings("jdbc:mysql","localhost","db_knock","test","test",3306));
    @NotNull
    private final WebSocketService webSocketService= new WebSocketServiceImpl();
    @NotNull
    private final GameMechanics gameMechanics = new GameMechanicsImpl(webSocketService,dbService);

}
