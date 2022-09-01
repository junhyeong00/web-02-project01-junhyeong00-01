package application;

import models.Buyer;
import models.Post;
import models.Seller;
import models.Transaction;
import models.User;
import panels.ImagePanel;
import panels.LoginPanel;
import panels.PostWritePanel;
import panels.SearchPanel;
import panels.TransactionsPanel;
import panels.postsPanel;
import utils.PostLoader;
import utils.TransactionLoader;
import utils.UserLoader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.util.List;

public class SecondHandMarket {
    private final List<Post> posts;
    private final List<User> users;
    private final List<Transaction> transactions;

    private JFrame frame;
    private JPanel contentPanel;
    private JPanel loginPanel;
    private ImagePanel imagePanel;
    private JPanel menuPanel;

    private Seller seller = new Seller(-1, "");
    private Buyer buyer = new Buyer(-1, "");

    public static void main(String[] args) throws FileNotFoundException {
        SecondHandMarket application = new SecondHandMarket();
        application.run();
    }

    public SecondHandMarket() throws FileNotFoundException {
        PostLoader postLoader = new PostLoader();
        posts = postLoader.loadPosts();

        UserLoader userLoader = new UserLoader();
        users = userLoader.loadUsers();

        TransactionLoader transactionLoader = new TransactionLoader();
        transactions = transactionLoader.loadTransactions();
    }

    public void run() {
        initFrame();

        initMenu();

        initContentPanel();

        frame.setVisible(true);
    }

    public void initFrame() {
        frame = new JFrame("우리 마켓");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocation(200, 100);

        imagePanel = new ImagePanel("data/background2.png");
        imagePanel.setLayout(new BorderLayout());
        frame.add(imagePanel);
        frame.setVisible(true);
    }

    public void initMenu() {
        menuPanel = new JPanel();
        menuPanel.setOpaque(false);

        menuPanel.add(homeButton());
        menuPanel.add(searchButton());
        menuPanel.add(postWriteButton());
        menuPanel.add(transactionListButton());
        menuPanel.add(logoutButton());

        imagePanel.add(menuPanel, BorderLayout.PAGE_START);
    }

    private JButton homeButton() {
        JButton button = new JButton("홈");
        button.setPreferredSize(new Dimension(130, 45));
        button.addActionListener(e -> {
            if (seller.id() != -1) {
                JPanel postPanel = new postsPanel(posts, seller, buyer, transactions);

                showContentPanel(postPanel);
            }
        });
        return button;
    }

    private JButton searchButton() {
        JButton button = new JButton("검색");
        button.setPreferredSize(new Dimension(130, 45));
        button.addActionListener(e -> {
            if (seller.id() != -1) {
                JPanel searchPanel = new SearchPanel(posts, seller, buyer, transactions);

                showContentPanel(searchPanel);
            }
        });
        return button;
    }

    private JButton postWriteButton() {
        JButton button = new JButton("판매 글 등록");
        button.setPreferredSize(new Dimension(130, 45));
        button.addActionListener(e -> {
            if (seller.id() != -1) {
                JPanel postWritePanel = new PostWritePanel(posts, seller);

                showContentPanel(postWritePanel);
            }
        });
        return button;
    }

    private JButton transactionListButton() {
        JButton button = new JButton("내 거래 목록");
        button.setPreferredSize(new Dimension(130, 45));
        button.addActionListener(e -> {
            if (seller.id() != -1) {
                JPanel transactionListPanel = new TransactionsPanel(posts, seller, buyer, transactions);

                showContentPanel(transactionListPanel);
            }
        });
        return button;
    }

    private JButton logoutButton() {
        JButton button = new JButton("로그아웃");
        button.setPreferredSize(new Dimension(130, 45));
        button.addActionListener(e -> {
            seller.logout();
            buyer.logout();
            loginPanel = new LoginPanel(users, seller, buyer);

            showContentPanel(loginPanel);
        });
        return button;
    }

    private void initContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setOpaque(false);

        loginPanel = new LoginPanel(users, seller, buyer);
        showContentPanel(loginPanel);

        imagePanel.add(contentPanel);
    }

    private void showContentPanel(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel);

        contentPanel.setVisible(false);
        contentPanel.setVisible(true);

        frame.setVisible(true);
    }
}
