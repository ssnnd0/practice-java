import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class AsciiPlatformer extends JPanel implements ActionListener {
    private static final int WIDTH = 80, HEIGHT = 20;
    private static final int PLAYER_START_X = 10, PLAYER_START_Y = HEIGHT - 2;
    private static final char PLAYER_CHAR = '@', SPIKE_CHAR = '^', PLATFORM_CHAR = '=', EMPTY_CHAR = ' ';
    private static final int GRAVITY = 1, MAX_JUMPS = 2;
    private int playerX, playerY, playerVelocityY, jumpCount, health, specialCooldown, points;
    private boolean doubleJump, gameOver, inMenu, inShop;
    private Timer timer;

    public AsciiPlatformer() {
        setPreferredSize(new Dimension(WIDTH * 8, HEIGHT * 16));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleInput(e);
            }
        });
        resetGame();
        timer = new Timer(1, this);
        timer.start();
    }

    private void handleInput(KeyEvent e) {
        if (gameOver) {
            resetGame();
            return;
        }
        if (inMenu) {
            inMenu = false;
            return;
        }
        if (inShop) {
            handleShopInput(e);
            return;
        }
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> playerX--;
            case KeyEvent.VK_RIGHT -> playerX++;
            case KeyEvent.VK_UP -> jump();
            case KeyEvent.VK_COMMA -> specialAbility();
            case KeyEvent.VK_PERIOD -> doubleJump = true; // Activate double jump
            case KeyEvent.VK_S -> inShop = true; // Open shop
        }
    }

    private void handleShopInput(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_1) { // Buy double jump
            if (points >= 5) {
                doubleJump = true;
                points -= 5;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_2) { // Buy special ability
            if (points >= 10) {
                specialCooldown = 1000; // Reset cooldown
                points -= 10;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { // Exit shop
            inShop = false;
        }
    }

    private void jump() {
        if (jumpCount < MAX_JUMPS) {
            playerVelocityY = -5;
            jumpCount++;
        }
    }

    private void specialAbility() {
        if (specialCooldown == 0) {
            playerVelocityY -= 3; // Boost jump
            specialCooldown = 1000; // Cooldown time
        }
    }

    private void resetGame() {
        playerX = PLAYER_START_X;
        playerY = PLAYER_START_Y;
        playerVelocityY = 0;
        jumpCount = 0;
        health = 3; // Health bar
        specialCooldown = 0;
        doubleJump = false;
        gameOver = false;
        points = 0; // Initialize points
        inMenu = true;
        inShop = false;
    }

    private void drawMenu(Graphics g) {
        String menu = """
                \n
                ███████╗ ██████╗ ██╗   ██╗███╗   ██╗███╗   ██╗██╗   ██╗██╗
                ██╔════╝██╔═══██╗██║   ██║████╗  ██║████╗  ██║██║   ██║██║
                ███████╗██║   ██║██║   ██║██╔██╗ ██║██╔██╗ ██║██║   ██║██║
                ╚════██║██║   ██║██║   ██║██║╚██╗██║██║╚██╗██║██║   ██║██║
                ███████║╚██████╔╝╚██████╔╝██║ ╚═╝██║██║ ╚═╝██║╚██████╔╝██║
                ╚══════╝ ╚═════╝  ╚═════╝ ╚═╝     ╚═╝╚═╝     ╚═╝ ╚═════╝ ╚ ═╝
                \n
                Press any key to start...
                """;
        g.setColor(Color.WHITE);
        g.drawString(menu, 10, 10);
    }

    private void drawShop(Graphics g) {
        String shop = """
                \n
                ███████╗ ██████╗ ██╗   ██╗███╗   ██╗███╗   ██╗██╗   ██╗██╗
                ██╔════╝██╔═══██╗██║   ██║████╗  ██║████╗  ██║██║   ██║██║
                ███████╗██║   ██║██║   ██║██╔██╗ ██║██╔██╗ ██║██║   ██║██║
                ╚════██║██║   ██║██║   ██║██║╚██╗██║██║╚██╗██║██║   ██║██║
                ███████║╚██████╔╝╚██████╔╝██║ ╚═╝██║██║ ╚═╝██║╚██████╔╝██║
                ╚══════╝ ╚═════╝  ╚═════╝ ╚═╝     ╚═╝╚═╝     ╚═╝ ╚═════╝ ╚═╝
                \n
                Points: """ + points + """
                1. Double Jump (5 points)
                2. Special Ability (10 points)
                Press ESC to exit...
                """;
        g.setColor(Color.WHITE);
        g.drawString(shop, 10, 10);
    }

    private void drawHealthBar(Graphics g) {
        g.setColor(Color.RED);
        g.drawString("Health: ", 1, HEIGHT - 1);
        for (int i = 0; i < health; i++) {
            g.drawString("█", 8 + i, HEIGHT - 1);
        }
    }

    private void drawSpecialCooldown(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawString("Special Cooldown: " + specialCooldown / 100, 1, HEIGHT - 2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inMenu) {
            drawMenu(g);
            return;
        }
        if (inShop) {
            drawShop(g);
            return;
        }
        StringBuilder screen = new StringBuilder();
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (x == playerX && y == playerY) {
                    g.setColor(Color.GREEN);
                    screen.append(PLAYER_CHAR);
                } else if (y == HEIGHT - 1 && (x % 5 == 0)) {
                    g.setColor(Color.WHITE);
                    screen.append(PLATFORM_CHAR);
                } else if (x == 0 && y == HEIGHT - 2) {
                    g.setColor(Color.RED);
                    screen.append(SPIKE_CHAR);
                } else {
                    g.setColor(Color.BLACK);
                    screen.append(EMPTY_CHAR);
                }
            }
            screen.append("\n");
        }
        drawHealthBar(g);
        drawSpecialCooldown(g);
        if (gameOver) {
            g.setColor(Color.RED);
            g.drawString("Game Over! Press any key to restart.", 1, HEIGHT - 3);
        }
        g.drawString(screen.toString(), 10, 10);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            playerY += playerVelocityY;
            playerVelocityY += GRAVITY;

            if (playerY >= HEIGHT - 1) {
                gameOver = true; // Hit the ground
            }

            if (playerY < HEIGHT - 2) {
                playerY = HEIGHT - 2; // Stay on platform
                jumpCount = 0;
            }

            if (specialCooldown > 0) {
                specialCooldown--;
            }

            repaint();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ascii Platformer - ssnnd0");
        AsciiPlatformer game = new AsciiPlatformer();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
