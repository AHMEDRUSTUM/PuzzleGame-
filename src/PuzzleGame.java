
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class PuzzleGame extends JFrame {

    private JPanel panel;
    private JButton[][] buttons;
    private int size = 4;

    public PuzzleGame(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);

        panel = new JPanel(new GridLayout(size, size));
        buttons = new JButton[size][size];

        ArrayList numbers = new ArrayList();
        for (int i = 0; i < size * size - 1; i++) {
            numbers.add(i + 1);
        }
        numbers.add(null); // Representing the empty tile

        Collections.shuffle(numbers);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int index = i * size + j;
                JButton button = new JButton(numbers.get(index) == null ? "" : String.valueOf(numbers.get(index)));
                button.addActionListener(new ButtonClickListener());
                buttons[i][j] = button;
                panel.add(button);

            }

            this.add(panel);
            this.setVisible(true);
        }
    }

    private class ButtonClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            int row = -1, col = -1;
            // Find the button in the grid
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (buttons[i][j] == button) {
                        row = i;
                        col = j;
                        break;
                    }
                }
            }
            // Check if the clicked tile is movable
            if (row > 0 && buttons[row - 1][col].getText().isEmpty()) { // Move up
                swapButtons(buttons[row][col], buttons[row - 1][col]);
            } else if (row < size - 1 && buttons[row + 1][col].getText().isEmpty()) { // Move down
                swapButtons(buttons[row][col], buttons[row + 1][col]);
            } else if (col > 0 && buttons[row][col - 1].getText().isEmpty()) { // Move left
                swapButtons(buttons[row][col], buttons[row][col - 1]);
            } else if (col < size - 1 && buttons[row][col + 1].getText().isEmpty()) { // Move right
                swapButtons(buttons[row][col], buttons[row][col + 1]);
            }

        }

        private void swapButtons(JButton b1, JButton b2) {
            String temp = b1.getText();
            b1.setText(b2.getText());
            b2.setText(temp);
        }

        private boolean checkWin() {
            int num = 1;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (i == size - 1 && j == size - 1) {
                        return true; // Last button should be empty for the win
                    }
                    if (!buttons[i][j].getText().equals(String.valueOf(num))) {
                        return false;
                    }
                    num++;
                }
            }
            return true;
        }
    }

    public static void main(String[] args) {
        PuzzleGame pn = new PuzzleGame("Puzzle Game");
    }
}
