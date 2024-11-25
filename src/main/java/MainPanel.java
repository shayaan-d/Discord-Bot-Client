import org.javacord.api.entity.Nameable;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;

import javax.swing.*;
import java.awt.*;
/*
 * Created by JFormDesigner on Fri Nov 22 23:59:46 EST 2024
 */



/**
 * @author shayaan
 */
public class MainPanel extends JPanel {
    public MainPanel() {
        initComponents();
    }


    private Server server;
    private ServerTextChannel channel;

    private void initComponents() {

        // BG
        setBackground(Main.blurple);
        setLayout(new GridLayout(8, 1));

        // Title
//      JLabel imageLabel = new JLabel();
//	    BufferedImage image = null;
//	    try {
//		    image = ImageIO.read(Main.class.getResourceAsStream("discord-logo-white.png"));
//	    } catch (IOException e) {
//		    throw new RuntimeException(e);
//	    }
//	    Image dimg = image.getScaledInstance(652, 195, Image.SCALE_SMOOTH);
//      ImageIcon icon = new ImageIcon(dimg);
//      imageLabel.setIcon(icon);
//      add(imageLabel);

        addLabel("Discord Bot Client", GG.ggSansBold);

        // Server Selector

        addLabel("Which server do you want to send a message in?", GG.ggSansSemiBold);

        JList<String> serverList = new JList<>();
        serverList.setFont(GG.ggSansReg);
        serverList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultListModel<String> serverListModel = new DefaultListModel<>();
        serverListModel.addAll(Cache.getServers().stream().map(Nameable::getName).toList());
        serverList.setModel(serverListModel);
        JScrollPane serverJCP = new JScrollPane(serverList);
        add(serverJCP);


        // Channel Selector

        addLabel("Which channel?", GG.ggSansSemiBold);

        JList<String> channelList = new JList<>();
        channelList.setFont(GG.ggSansReg);
        channelList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultListModel<String> channelListModel = new DefaultListModel<>();
        channelList.setModel(channelListModel);
        JScrollPane channelJCP = new JScrollPane(channelList);
        add(channelJCP);

        serverList.addListSelectionListener(e -> {
            channelListModel.removeAllElements();
            String serverName = serverList.getSelectedValue();
            server = Main.getServerFromName(serverName);
            channelListModel.addAll(server.getTextChannels().stream().map(Nameable::getName).toList());
        });

        channelList.addListSelectionListener(e -> {
            channel = Main.getChannelFromName(server, channelList.getSelectedValue());
        });

        addLabel("What would you like to say?", GG.ggSansSemiBold);

        JTextArea messageBox = new JTextArea();
        messageBox.setFont(GG.ggSansReg);
        add(messageBox);

        JButton send = new JButton("Send Message");
        send.setFont(GG.ggSansMed);
        send.addActionListener(e -> {

            String message = messageBox.getText();

            if (channel == null) {
                JOptionPane.showMessageDialog(null,
                        "Select a channel first!", "Error: No Channel Selected",
                        JOptionPane.WARNING_MESSAGE);
            }

            channel.sendMessage(message);

        });
        add(send);

    }

    private JLabel addLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(font);
        add(label);
        return label;
    }

}
