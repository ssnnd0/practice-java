import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class BasicPlatformer extends JPanel implements ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int PLATFORM_COUNT = 10;
    private static final int PLATFORM_MIN_WIDTH = 50;
    private static final int PLATFORM_MAX_WIDTH = 150;
    private static final int PLATFORM_HEIGHT = 20;
    private static final int PLAYER_SIZE = 30;

    private Timer timer;
    private ArrayList<Rectangle> platforms;
    private Rectangle player;
    private int playerVelocityY = 0;
    private boolean isJumping = false;

    public BasicPlatformer() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.CYAN);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    player.x -= 10; // Move left
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    player.x += 10; // Move right
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE && !isJumping) {
                    playerVelocityY = -15; // Jump
                    isJumping = true;
                }
            }
        });

        platforms = new ArrayList<>();
        generatePlatforms();
        player = new Rectangle(WIDTH / 2, HEIGHT - PLAYER_SIZE - PLATFORM_HEIGHT, PLAYER_SIZE, PLAYER_SIZE);

        timer = new Timer(20, this);
        timer.start();
    }

    private void generatePlatforms() {
        Random rand = new Random();
        for (int i = 0; i < PLATFORM_COUNT; i++) {
            int platformWidth = PLATFORM_MIN_WIDTH + rand.nextInt(PLATFORM_MAX_WIDTH - PLATFORM_MIN_WIDTH);
            int platformX = rand.nextInt(WIDTH - platformWidth);
            int platformY = HEIGHT - (rand.nextInt(HEIGHT / 2) + PLATFORM_HEIGHT);
            platforms.add(new Rectangle(platformX, platformY, platformWidth, PLATFORM_HEIGHT));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GREEN);
        for (Rectangle platform : platforms) {
            g.fillRect(platform.x, platform.y, platform.width, platform.height);
        }

        g.setColor(Color.RED);
        g.fillRect(player.x, player.y, player.width, player.height);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.y += playerVelocityY;

        // Gravity
        playerVelocityY += 1;

        // Check for collision with platforms
        for (Rectangle platform : platforms) {
            if (player.intersects(platform)) {
                player.y = platform.y - PLAYER_SIZE; // Reset player position on platform
                playerVelocityY = 0; // Stop falling
                isJumping = false; // Allow jumping again
                break;
            }
        }

        // Check boundaries
        if (player.y >= HEIGHT - PLAYER_SIZE) {
            player.y = HEIGHT - PLAYER_SIZE; // Keep player on the ground
            playerVelocityY = 0; // Stop falling
            isJumping = false; // Allow jumping again
        }

        // Repaint the screen
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Basic Platformer");
        BasicPlatformer game = new BasicPlatformer();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
