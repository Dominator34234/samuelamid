import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShoppingListApp extends JFrame {

    private JTextField itemInput;
    private JButton addButton;
    private JButton deleteButton;
    private JList<String> itemsList;
    private DefaultListModel<String> listModel;
    private JLabel counterLabel;

    public ShoppingListApp() {
        setTitle("Menedżer Zadań");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        listModel = new DefaultListModel<>();

        itemInput = new JTextField();
        addButton = new JButton("Dodaj");
        deleteButton = new JButton("Usuń zaznaczone");
        itemsList = new JList<>(listModel);
        counterLabel = new JLabel("Liczba zadań: 0");

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(itemInput, BorderLayout.CENTER);
        topPanel.add(addButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(itemsList), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(counterLabel, BorderLayout.WEST);
        bottomPanel.add(deleteButton, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addTask());

        deleteButton.addActionListener(e -> deleteSelectedTask());

        itemsList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = itemsList.locationToIndex(e.getPoint());
                    if (index != -1) {
                        listModel.remove(index);
                        updateCounter();
                    }
                }
            }
        });
    }

    private void addTask() {
        String task = itemInput.getText().trim();

        if (task.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Zadanie nie może być puste!",
                    "Błąd",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        listModel.addElement(task);
        itemInput.setText("");
        updateCounter();
    }

    private void deleteSelectedTask() {
        int index = itemsList.getSelectedIndex();

        if (index == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Najpierw zaznacz zadanie do usunięcia!",
                    "Błąd",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        listModel.remove(index);
        updateCounter();
    }

    private void updateCounter() {
        counterLabel.setText("Liczba zadań: " + listModel.getSize());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ShoppingListApp app = new ShoppingListApp();
            app.setVisible(true);
        });
    }
}
