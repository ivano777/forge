package forge.screens.home;

import java.util.ArrayList;

import forge.screens.FScreen;
import forge.screens.LoadingOverlay;
import forge.Forge;
import forge.Graphics;
import forge.assets.FSkinImage;
import forge.screens.constructed.ConstructedScreen;
import forge.screens.draft.DraftScreen;
import forge.screens.gauntlet.GauntletScreen;
import forge.screens.quest.QuestMenu;
import forge.screens.quest.QuestMenu.LaunchReason;
import forge.screens.sealed.SealedScreen;
import forge.screens.settings.SettingsScreen;
import forge.toolbox.FButton;
import forge.toolbox.FEvent;
import forge.toolbox.FEvent.FEventHandler;

public class HomeScreen extends FScreen {
    public static final float LOGO_SIZE_FACTOR = 0.7f;
    public static final float INSETS_FACTOR = 0.025f;
    private static final float GAP_Y_FACTOR = 0.01f;
    private final ArrayList<FButton> buttons = new ArrayList<FButton>();

    public HomeScreen() {
        super((Header)null);

        addButton("Constructed", new FEventHandler() {
            @Override
            public void handleEvent(FEvent e) {
                Forge.openScreen(new ConstructedScreen());
            }
        });
        addButton("Booster Draft", new FEventHandler() {
            @Override
            public void handleEvent(FEvent e) {
                Forge.openScreen(new DraftScreen());
            }
        });
        addButton("Sealed Deck", new FEventHandler() {
            @Override
            public void handleEvent(FEvent e) {
                Forge.openScreen(new SealedScreen());
            }
        });
        addButton("Quest Mode", new FEventHandler() {
            @Override
            public void handleEvent(FEvent e) {
                QuestMenu.launchQuestMode(LaunchReason.StartQuestMode);
            }
        });
        addButton("Gauntlets", new FEventHandler() {
            @Override
            public void handleEvent(FEvent e) {
                LoadingOverlay.show("Loading gauntlets...", new Runnable() {
                    @Override
                    public void run() {
                        Forge.openScreen(new GauntletScreen());
                    }
                });
            }
        });
        addButton("Settings", new FEventHandler() {
            @Override
            public void handleEvent(FEvent e) {
                SettingsScreen.show();
            }
        });
    }

    private void addButton(String caption, FEventHandler command) {
        buttons.add(add(new FButton(caption, command)));
    }

    @Override
    protected void doLayout(float startY, float width, float height) {
        float x = width * INSETS_FACTOR;
        float y = width * LOGO_SIZE_FACTOR + 2 * x; //start below background logo
        float dy = height * GAP_Y_FACTOR;
        float buttonWidth = width - 2 * x;
        float buttonHeight = (height - y - x) / buttons.size() - dy;
        dy += buttonHeight;

        for (FButton button : buttons) {
            button.setBounds(x, y, buttonWidth, buttonHeight);
            y += dy;
        }
    }

    @Override
    protected void drawBackground(Graphics g) {
        super.drawBackground(g);

        float size = getWidth() * LOGO_SIZE_FACTOR;
        float x = (getWidth() - size) / 2f;
        float y = getWidth() * INSETS_FACTOR;
        g.drawImage(FSkinImage.LOGO, x, y, size, size);
    }
}
