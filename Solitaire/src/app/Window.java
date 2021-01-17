package app;

import mechanic.Game;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Window extends JFrame
{
    static JLabel label;
    static Color pawnColor;
    static Color boardColor;
    static boolean fillPawn;
    private Panel panel;
    private boolean european;
    private JMenuItem jumpUp;
    private JMenuItem jumpLeft;
    private JMenuItem jumpRight;
    private JMenuItem jumpDown;
    private JRadioButtonMenuItem eu;
    private JRadioButtonMenuItem br;
    private ButtonGroup group;
    public Window()
    {
        super("Solitaire");
        pawnColor = Color.RED;
        boardColor = Color.WHITE;
        fillPawn = true;
        european = false;
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu moves = new JMenu("Moves");
        JMenu settings = new JMenu("Settings");
        JMenu help = new JMenu("Help");
        file.setMnemonic(KeyEvent.VK_F);
        moves.setMnemonic(KeyEvent.VK_M);
        moves.addMenuListener(new MovesMenuListener());
        settings.setMnemonic(KeyEvent.VK_S);
        settings.addMenuListener(new SettingsMenuListener());
        help.setMnemonic(KeyEvent.VK_H);
        menuBar.add(file);
        menuBar.add(moves);
        menuBar.add(settings);
        menuBar.add(Box.createGlue());
        menuBar.add(help);
        initFile(file);
        initMoves(moves);
        initSettings(settings);
        initHelp(help);
        panel = new Panel();
        label = new JLabel(" ");
        setJMenuBar(menuBar);
        add(panel, BorderLayout.CENTER);
        add(label, BorderLayout.PAGE_END);
        setMinimumSize(new Dimension(400, 460));
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    private void initFile(JMenu f)
    {
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem exit = new JMenuItem("Exit");
        f.add(newGame);
        f.addSeparator();
        f.add(exit);
        newGame.addActionListener(e -> {
            Main.game = new Game(european);
            label.setText(" ");
            panel.checkMap();
        });
        exit.addActionListener(e -> System.exit(0));
    }
    private void initMoves(JMenu m)
    {
        JMenuItem reset = new JMenuItem("Reset focus");
        JMenuItem goUp = new JMenuItem("Move up");
        JMenuItem goLeft = new JMenuItem("Move left");
        JMenuItem goRight = new JMenuItem("Move right");
        JMenuItem goDown = new JMenuItem("Move down");
        jumpUp = new JMenuItem("Jump up");
        jumpLeft = new JMenuItem("Jump left");
        jumpRight = new JMenuItem("Jump right");
        jumpDown = new JMenuItem("Jump down");
        reset.setAccelerator(KeyStroke.getKeyStroke("control R"));
        goUp.setAccelerator(KeyStroke.getKeyStroke("control W"));
        goLeft.setAccelerator(KeyStroke.getKeyStroke("control A"));
        goRight.setAccelerator(KeyStroke.getKeyStroke("control D"));
        goDown.setAccelerator(KeyStroke.getKeyStroke("control S"));
        jumpUp.setAccelerator(KeyStroke.getKeyStroke("control  shift W"));
        jumpLeft.setAccelerator(KeyStroke.getKeyStroke("control  shift A"));
        jumpRight.setAccelerator(KeyStroke.getKeyStroke("control  shift D"));
        jumpDown.setAccelerator(KeyStroke.getKeyStroke("control  shift S"));
        m.add(reset);
        m.add(goUp);
        m.add(goLeft);
        m.add(goRight);
        m.add(goDown);
        m.addSeparator();
        m.add(jumpUp);
        m.add(jumpLeft);
        m.add(jumpRight);
        m.add(jumpDown);
        reset.addActionListener(e -> panel.resetFocus());
        goUp.addActionListener(e -> panel.moveFocus(2));
        goLeft.addActionListener(e -> panel.moveFocus(1));
        goRight.addActionListener(e -> panel.moveFocus(3));
        goDown.addActionListener(e -> panel.moveFocus(4));
        jumpUp.addActionListener(e -> panel.jumpFocus(2));
        jumpLeft.addActionListener(e -> panel.jumpFocus(1));
        jumpRight.addActionListener(e -> panel.jumpFocus(3));
        jumpDown.addActionListener(e -> panel.jumpFocus(4));
    }
    private void initSettings(JMenu s)
    {
        eu = new JRadioButtonMenuItem("European");
        br = new JRadioButtonMenuItem("British");
        JMenuItem boardColor = new JMenuItem("Board color");
        JMenuItem pawnColor = new JMenuItem("Pawn color");
        JCheckBoxMenuItem fill = new JCheckBoxMenuItem("Fill pawns");
        group = new ButtonGroup();
        group.add(br);
        group.add(eu);
        br.setSelected(true);
        fill.setSelected(true);
        s.add(br);
        s.add(eu);
        s.addSeparator();
        s.add(boardColor);
        s.add(pawnColor);
        s.add(fill);
        br.addActionListener(e -> {
            european = false;
            Main.game = new Game(false);
            label.setText(" ");
            panel.checkMap();
        });
        eu.addActionListener(e -> {
            european = true;
            Main.game = new Game(true);
            label.setText(" ");
            panel.checkMap();
        });
        boardColor.addActionListener(e -> {
            this.boardColor = JColorChooser.showDialog(null, "Choose a board color", this.boardColor);
            panel.checkMap();
        });
        pawnColor.addActionListener(e -> {
            this.pawnColor = JColorChooser.showDialog(null, "Choose a board color", this.pawnColor);
            panel.checkMap();
        });
        fill.addActionListener(e -> {
            fillPawn = !fillPawn;
            panel.checkMap();
        });
    }
    private void initHelp(JMenu h)
    {
        JMenuItem app = new JMenuItem("Application");
        JMenuItem rules = new JMenuItem("Rules");
        h.add(app);
        h.add(rules);
        app.addActionListener(e -> JOptionPane.showMessageDialog(null, "Solitaire\nAuthor: Michał Wójtowicz\nVersion: 1.0\nDate: 27.11.2020", "Application", JOptionPane.INFORMATION_MESSAGE));
        rules.addActionListener(e -> JOptionPane.showMessageDialog(null, "The goal is to leave only one peg on the board by jumping diagonally over other pegs to empty spaces.", "Rules", JOptionPane.INFORMATION_MESSAGE));
    }
    class MovesMenuListener implements MenuListener
    {
        public void menuSelected(MenuEvent e)
        {
            int f = panel.getFocus();
            jumpLeft.setEnabled(false);
            jumpUp.setEnabled(false);
            jumpRight.setEnabled(false);
            jumpDown.setEnabled(false);
            if (f >= 1 && f <= 49)
            {
                if (Main.game.getLeft(f)) jumpLeft.setEnabled(true);
                if (Main.game.getRight(f)) jumpRight.setEnabled(true);
                if (Main.game.getUp(f)) jumpUp.setEnabled(true);
                if (Main.game.getDown(f)) jumpDown.setEnabled(true);
            }
        }
        public void menuDeselected(MenuEvent e) { }
        public void menuCanceled(MenuEvent e) { }
    }
    class SettingsMenuListener implements MenuListener
    {
        public void menuSelected(MenuEvent e)
        {
            br.setEnabled(false);
            eu.setEnabled(false);
            if (Main.game.getFinish() || !Main.game.getStarted())
            {
                br.setEnabled(true);
                eu.setEnabled(true);
            }
        }
        public void menuDeselected(MenuEvent e) { }
        public void menuCanceled(MenuEvent e) { }
    }
}
