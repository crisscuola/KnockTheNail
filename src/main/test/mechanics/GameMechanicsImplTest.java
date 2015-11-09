package mechanics;

import base.GameMechanics;
import base.GameUser;
import base.WebSocketService;
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
    private final WebSocketService webSocketService= new WebSocketServiceImpl();
    @NotNull
    private final GameMechanics gameMechanics = new GameMechanicsImpl(webSocketService);

    @Test
    public void testAddUser() throws Exception{
        UserProfile user = new UserProfile("1","1");
        user.setId(1);
        boolean result = gameMechanics.addUser(user);
        assertTrue(result);
        boolean result2 = gameMechanics.addUser(user);
        assertFalse(result2);
        boolean result3 = gameMechanics.removeUserToGame(user);
        assertTrue(result3);
    }
}
