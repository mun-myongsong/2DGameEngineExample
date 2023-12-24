package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
    private boolean[] isKeyPressed;
    private byte[] keyPressedTime;

    public Keyboard() {
        isKeyPressed = new boolean[0xFFFF];
        keyPressedTime = new byte[0xFFFF];
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        isKeyPressed[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        isKeyPressed[e.getKeyCode()] = false;
    }

    private void handleKeyPressTime(int keyCode) {
        if (isKeyPressed[keyCode]) {
            if (keyPressedTime[keyCode] < Byte.MAX_VALUE) {
                keyPressedTime[keyCode]++;
            }
        } else {
            keyPressedTime[keyCode] = 0;
        }
    }

    public boolean isJustPressedKey(int keyCode) {
        return keyPressedTime[keyCode] == 1;
    }

    public boolean isPressedKey(int keyCode) {
        return keyPressedTime[keyCode] > 0;
    }

    public void update() {
        handleKeyPressTime(KeyEvent.VK_A);
        handleKeyPressTime(KeyEvent.VK_D);
        handleKeyPressTime(KeyEvent.VK_S);
        handleKeyPressTime(KeyEvent.VK_W);
    }
}
