package udistrital.avanzada.duelosmagicos.Vista;

import java.awt.*;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Clase PanelDuelo. Representa el escenario visual del duelo. SOLO maneja la
 * parte visual (sin lógica de control).
 */
public class PanelDuelo extends JPanel {

    // ===========================
    // Componentes visuales
    // ===========================
    private JLabel lblMago1, lblMago2;
    private JLabel lblLanzamiento1, lblLanzamiento2;
    private JLabel lblAturdido1, lblAturdido2;
    private JLabel lblNombreMago1, lblNombreMago2;
    private JLabel lblHechizo, lblExplosion;
    private JLabel lblEstadoDuelo;
    private JButton btnIniciarDuelo;
    private Image fondo;

    // ===========================
    // Timers y estados visuales
    // ===========================
    private Timer mensajeHighlightTimer;
    private Timer animacionHechizoTimer;
    private Timer timerExplosion;
    private StringBuilder historialMensajes = new StringBuilder();
    private final int MAX_LINEAS = 5;

    // ===========================
    // Constantes visuales
    // ===========================
    private final int posMago1X = 150;
    private final int posMago2X = 700;
    private final int posMagoY = 365;
    private final int anchoMago = 150;
    private final Color COLOR_BASE = new Color(255, 230, 255);
    private final Color COLOR_RESALTADO = new Color(255, 240, 150);

    public PanelDuelo() {
        setLayout(null);
        setOpaque(true);
        setBorder(crearMarcoDecorativo());

        cargarFondo();
        inicializarMagos();
        inicializarComponentes();
        agregarComponentes();
    }

    // ---------------------------
    // Inicialización
    // ---------------------------
    private void cargarFondo() {
        try {
            fondo = new ImageIcon(getClass().getResource(
                    "/udistrital/avanzada/duelosmagicos/recursos/fondo.png")).getImage();
        } catch (Exception e) {
            System.err.println("No se pudo cargar fondo.png: " + e.getMessage());
            fondo = null;
        }
    }

    private void inicializarMagos() {
        lblMago1 = new JLabel(escalar("/udistrital/avanzada/duelosmagicos/recursos/mago1.png"));
        lblMago2 = new JLabel(escalar("/udistrital/avanzada/duelosmagicos/recursos/mago2.png"));
        lblMago1.setBounds(posMago1X, posMagoY, anchoMago, 128);
        lblMago2.setBounds(posMago2X, posMagoY, anchoMago, 128);

        // Sprites de lanzamiento y aturdido
        lblLanzamiento1 = new JLabel(escalar("/udistrital/avanzada/duelosmagicos/recursos/lanzamiento1.png"));
        lblLanzamiento2 = new JLabel(escalar("/udistrital/avanzada/duelosmagicos/recursos/lanzamiento2.png"));
        lblAturdido1 = new JLabel(escalar("/udistrital/avanzada/duelosmagicos/recursos/aturdido1.png"));
        lblAturdido2 = new JLabel(escalar("/udistrital/avanzada/duelosmagicos/recursos/aturdido2.png"));

        lblLanzamiento1.setBounds(posMago1X, posMagoY, anchoMago, 128);
        lblAturdido1.setBounds(posMago1X, posMagoY, anchoMago, 128);
        lblLanzamiento2.setBounds(posMago2X, posMagoY, anchoMago, 128);
        lblAturdido2.setBounds(posMago2X, posMagoY, anchoMago, 128);

        lblLanzamiento1.setVisible(false);
        lblLanzamiento2.setVisible(false);
        lblAturdido1.setVisible(false);
        lblAturdido2.setVisible(false);

        // nombres debajo de los magos
        lblNombreMago1 = new JLabel("", SwingConstants.CENTER);
        lblNombreMago1.setFont(new Font("Georgia", Font.BOLD, 14));
        lblNombreMago1.setForeground(new Color(255, 230, 255));
        lblNombreMago1.setBounds(posMago1X - 20, posMagoY + 130, anchoMago + 40, 24);

        lblNombreMago2 = new JLabel("", SwingConstants.CENTER);
        lblNombreMago2.setFont(new Font("Georgia", Font.BOLD, 14));
        lblNombreMago2.setForeground(new Color(255, 230, 255));
        lblNombreMago2.setBounds(posMago2X - 20, posMagoY + 130, anchoMago + 40, 24);
    }

    private void inicializarComponentes() {
        lblHechizo = new JLabel(escalar("/udistrital/avanzada/duelosmagicos/recursos/hechizo.png", 64, 64));
        lblExplosion = new JLabel(escalar("/udistrital/avanzada/duelosmagicos/recursos/explosion.png", 96, 96));
        lblHechizo.setVisible(false);
        lblExplosion.setVisible(false);

        btnIniciarDuelo = crearBoton("Iniciar Duelo", new Color(80, 30, 120));
        btnIniciarDuelo.setBounds(355, 530, 250, 45);

        lblEstadoDuelo = new JLabel("", SwingConstants.CENTER);
        lblEstadoDuelo.setFont(new Font("Georgia", Font.ITALIC, 17));
        lblEstadoDuelo.setForeground(new Color(255, 230, 255));
        lblEstadoDuelo.setVerticalAlignment(SwingConstants.TOP);
        lblEstadoDuelo.setOpaque(false);
        lblEstadoDuelo.setBounds(120, 200, 700, 120);
        lblEstadoDuelo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 220, 255, 120), 1),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
    }

    private void agregarComponentes() {
        add(lblMago1);
        add(lblMago2);
        add(lblLanzamiento1);
        add(lblLanzamiento2);
        add(lblAturdido1);
        add(lblAturdido2);
        add(lblNombreMago1);
        add(lblNombreMago2);
        add(lblHechizo);
        add(lblExplosion);
        add(btnIniciarDuelo);
        add(lblEstadoDuelo);
    }

    // ===========================
    // Métodos públicos de animación (llamados desde el Control)
    // ===========================
    /**
     * Anima visualmente el lanzamiento de un hechizo entre los magos. Muestra
     * un efecto de movimiento, brillo inicial y explosión en el impacto.
     *
     * @param indice 1 si lanza el mago izquierdo, 2 si lanza el mago derecho.
     */
    public void animarLanzamiento(int indice) {
        SwingUtilities.invokeLater(() -> {
            final boolean desdeMago1 = (indice == 1); // saber quién lanza

            // ===== Coordenadas base de lanzamiento e impacto =====
            final int inicioX = desdeMago1 ? posMago1X + 80 : posMago2X - 40;
            final int finX = desdeMago1 ? posMago2X - 30 : posMago1X + 80;
            final int posY = posMagoY + 10;

            // Asegura que el hechizo se vea por encima de los magos
            setComponentZOrder(lblHechizo, 0);
            lblHechizo.setVisible(true);
            lblHechizo.setLocation(inicioX, posY);

            // Cambia el sprite del mago para mostrarlo en posición de "lanzando"
            if (desdeMago1) {
                lblLanzamiento1.setVisible(true);
                lblMago1.setVisible(false);
            } else {
                lblLanzamiento2.setVisible(true);
                lblMago2.setVisible(false);
            }

            // ===== Brillo inicial del hechizo (efecto cinematográfico) =====
            lblHechizo.setSize(70, 70); // un poco más grande que su sprite original
            lblHechizo.setOpaque(true);
            lblHechizo.setBackground(new Color(255, 255, 180, 120)); // amarillo translúcido

            // Parámetros de animación
            final int paso = 30;       // píxeles avanzados por tick
            final int intervalo = 15; 
            final int duracionBrillo = 200; // tiempo que dura el brillo inicial

            // Si había una animación corriendo, la detenemos
            if (animacionHechizoTimer != null && animacionHechizoTimer.isRunning()) {
                animacionHechizoTimer.stop();
            }

            // ===== Timer que quita el brillo luego de 200 ms =====
            Timer brillo = new Timer(duracionBrillo, e -> {
                lblHechizo.setOpaque(false);
                lblHechizo.repaint();
            });
            brillo.setRepeats(false);
            brillo.start();

            // ===== Timer principal que mueve el hechizo =====
            animacionHechizoTimer = new Timer(intervalo, null);
            animacionHechizoTimer.addActionListener(ev -> {
                // Actualiza la posición en X (de izquierda a derecha o al revés)
                Point p = lblHechizo.getLocation();
                int nx = p.x + (desdeMago1 ? paso : -paso);
                lblHechizo.setLocation(nx, p.y);

                // Verifica si ya llegó al objetivo (impacto)
                boolean impacto = desdeMago1 ? (nx >= finX) : (nx <= finX);
                if (impacto) {
                    animacionHechizoTimer.stop(); // detiene el vuelo

                    // ===== Pausa breve antes del impacto visual =====
                    Timer pausaImpacto = new Timer(180, e2 -> {
                        lblHechizo.setVisible(false); // desaparece el hechizo

                        // Restaurar sprite del mago lanzador
                        if (desdeMago1) {
                            lblLanzamiento1.setVisible(false);
                            lblMago1.setVisible(true);
                        } else {
                            lblLanzamiento2.setVisible(false);
                            lblMago2.setVisible(true);
                        }

                        // ===== Mostrar efectos en el objetivo =====
                        int objetivo = desdeMago1 ? 2 : 1;
                        mostrarExplosionEn(objetivo); // animación de explosión
                        mostrarAturdido(objetivo, 900); // estado de aturdido
                    });
                    pausaImpacto.setRepeats(false);
                    pausaImpacto.start();
                }
            });

            animacionHechizoTimer.setRepeats(true);
            animacionHechizoTimer.start(); // inicia el vuelo
        });
    }

    public void mostrarAturdido(int indice, int duracionMs) {
        SwingUtilities.invokeLater(() -> {
            JLabel normal = (indice == 1) ? lblMago1 : lblMago2;
            JLabel aturdido = (indice == 1) ? lblAturdido1 : lblAturdido2;
            normal.setVisible(false);
            aturdido.setVisible(true);
            Timer t = new Timer(duracionMs, e -> {
                aturdido.setVisible(false);
                normal.setVisible(true);
            });
            t.setRepeats(false);
            t.start();
        });
    }

    public void mostrarExplosionEn(int indice) {
        SwingUtilities.invokeLater(() -> {
            int x = (indice == 1) ? posMago1X : posMago2X;
            int y = posMagoY - 20;
            lblExplosion.setBounds(x, y, 96, 96);
            lblExplosion.setVisible(true);

            if (timerExplosion != null && timerExplosion.isRunning()) {
                timerExplosion.stop();
            }
            timerExplosion = new Timer(700, e -> lblExplosion.setVisible(false));
            timerExplosion.setRepeats(false);
            timerExplosion.start();
        });
    }

    public void mostrarDanioFlotante(int indice, int puntos) {
        SwingUtilities.invokeLater(() -> {
            int baseX = (indice == 1) ? posMago1X : posMago2X;
            int startY = posMagoY - 20;

            JLabel lbl = new JLabel("+" + puntos, SwingConstants.CENTER);
            lbl.setFont(new Font("Georgia", Font.BOLD, 18));
            lbl.setSize(80, 28);
            lbl.setLocation(baseX + (anchoMago / 2) - 40, startY);

            // 🟢 color verde si ganó puntos
            lbl.setForeground(new Color(50, 220, 50));

            add(lbl);
            lbl.setVisible(true);
            repaint();

            // animación flotante
            Timer mover = new Timer(25, null);
            final int pasos = 18;
            final int desplazamiento = 2;
            final int[] contador = {0};
            mover.addActionListener(ev -> {
                lbl.setLocation(lbl.getX(), lbl.getY() - desplazamiento);
                contador[0]++;
                if (contador[0] >= pasos) {
                    ((Timer) ev.getSource()).stop();
                    remove(lbl);
                    repaint();
                }
            });
            mover.setRepeats(true);
            mover.start();
        });
    }

    // ===========================
    // Métodos públicos auxiliares
    // ===========================
    public JButton getBotonIniciarDuelo() {
        return btnIniciarDuelo;
    }

    public void setBotonIniciarActivo(boolean activo) {
        btnIniciarDuelo.setEnabled(activo);
    }

    public void setNombresMagos(String nombre1, String nombre2) {
        lblNombreMago1.setText(nombre1 != null ? nombre1 : "");
        lblNombreMago2.setText(nombre2 != null ? nombre2 : "");
    }

    public String getNombreMago(int indice) {
        if (indice == 1) {
            return lblNombreMago1.getText();
        }
        if (indice == 2) {
            return lblNombreMago2.getText();
        }
        return "";
    }

    // ===========================
    // Mensajes del duelo
    // ===========================
    public void mostrarMensajeDuelo(String mensaje) {
        mostrarMensajeDuelo(mensaje, COLOR_BASE, COLOR_RESALTADO);
    }

    public void mostrarMensajeDuelo(String mensaje, Color colorBase, Color colorResaltado) {
        // Agrega el nuevo mensaje arriba
        if (historialMensajes.length() > 0) {
            historialMensajes.insert(0, mensaje + "\n");
        } else {
            historialMensajes.append(mensaje);
        }

        // Limita el historial a las últimas MAX_LINEAS
        String[] lineas = historialMensajes.toString().split("\n");
        if (lineas.length > MAX_LINEAS) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < MAX_LINEAS; i++) {
                sb.append(lineas[i]).append("\n");
            }
            historialMensajes = sb;
            lineas = historialMensajes.toString().split("\n");
        }

        // Colores base y resaltado en formato HTML
        String baseHex = String.format("#%02X%02X%02X", colorBase.getRed(), colorBase.getGreen(), colorBase.getBlue());
        String resHex = String.format("#%02X%02X%02X", colorResaltado.getRed(), colorResaltado.getGreen(), colorResaltado.getBlue());

        // Construcción del HTML (nuevo mensaje arriba y resaltado)
        StringBuilder html = new StringBuilder("<html><div style='text-align:center; line-height:1.3em;'>");
        for (int i = 0; i < lineas.length; i++) {
            String esc = escapeHtml(lineas[i]);
            html.append("<span style='color:")
                    .append(i == 0 ? resHex : baseHex) // 🔹 Resalta el primero (nuevo)
                    .append(";'>").append(esc).append("</span>");
            if (i < lineas.length - 1) {
                html.append("<br>");
            }
        }
        html.append("</div></html>");
        lblEstadoDuelo.setText(html.toString());

        // Animación de desvanecimiento del resaltado
        if (mensajeHighlightTimer != null && mensajeHighlightTimer.isRunning()) {
            mensajeHighlightTimer.stop();
        }
        final String[] lineasCopia = lineas.clone();

        // 💫 Duración del resaltado sincronizada con la velocidad del hechizo
        mensajeHighlightTimer = new Timer(85, e -> {  // antes 1600 → ahora 400 ms
            SwingUtilities.invokeLater(() -> {
                StringBuilder html2 = new StringBuilder("<html><div style='text-align:center; line-height:1.3em;'>");
                for (String l : lineasCopia) {
                    html2.append("<span style='color:").append(baseHex).append(";'>")
                            .append(escapeHtml(l)).append("</span><br>");
                }
                html2.append("</div></html>");
                lblEstadoDuelo.setText(html2.toString());
            });
        });
        mensajeHighlightTimer.setRepeats(false);
        mensajeHighlightTimer.start();

    }

    // ===========================
    // Utilitarios
    // ===========================
    private String escapeHtml(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    private ImageIcon escalar(String ruta) {
        return escalar(ruta, anchoMago, 150);
    }

    private ImageIcon escalar(String ruta, int ancho, int alto) {
        URL url = getClass().getResource(ruta);
        if (url == null) {
            System.err.println("Imagen no encontrada: " + ruta);
            return new ImageIcon();
        }
        Image img = new ImageIcon(url).getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    private JButton crearBoton(String texto, Color colorBase) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint grad = new GradientPaint(0, 0, colorBase.brighter(), 0, getHeight(), colorBase.darker());
                g2.setPaint(grad);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.setColor(new Color(255, 220, 255));
                g2.setStroke(new BasicStroke(2f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Papyrus", Font.BOLD, 22));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return btn;
    }

    private Border crearMarcoDecorativo() {
        Color dorado = new Color(212, 175, 55);
        Color sombra = new Color(40, 20, 60, 120);
        Color luz = new Color(255, 235, 180);
        Border bordePrincipal = new LineBorder(dorado, 6, true);
        Border sombraExterior = new MatteBorder(10, 10, 10, 10, sombra);
        TitledBorder titulo = BorderFactory.createTitledBorder(
                bordePrincipal, "Duelo de Magos",
                TitledBorder.CENTER, TitledBorder.TOP,
                new Font("Old English Text MT", Font.BOLD, 28), luz
        );
        return new CompoundBorder(sombraExterior, titulo);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (fondo != null) {
            g2.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        } else {
            GradientPaint grad = new GradientPaint(0, 0, new Color(30, 0, 60),
                    0, getHeight(), new Color(100, 40, 150));
            g2.setPaint(grad);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
        g2.setColor(new Color(20, 0, 30, 80));
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}
