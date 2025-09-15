import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MiniTienda {
    public static void main(String[] args) {
        ArrayList<String> nombres = new ArrayList<>(); // nombres de productos
        double[] precios = new double[0]; // precios
        HashMap<String, Integer> stock = new HashMap<>(); // stock por producto

        double totalCompras = 0; // acumulador de compras

        int opcion;
        do {
            // Menu principal
            String menu = "=== MINI TIENDA ===\n"
                    + "1. Agregar producto\n"
                    + "2. Listar inventario\n"
                    + "3. Comprar producto\n"
                    + "4. Mostrar estadisticas\n"
                    + "5. Buscar producto\n"
                    + "0. Salir\n"
                    + "Elige una opcion:";
            String entrada = JOptionPane.showInputDialog(menu);

            if (entrada == null)
                break;
            try {
                opcion = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Opcion invalida");
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    String nombre = JOptionPane.showInputDialog("Nombre del producto:");
                    if (nombre == null || nombre.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Nombre invalido");
                        break;
                    }
                    if (nombres.contains(nombre)) {
                        JOptionPane.showMessageDialog(null, "El producto ya existe");
                        break;
                    }
                    String pStr = JOptionPane.showInputDialog("Precio del producto:");
                    String sStr = JOptionPane.showInputDialog("Stock inicial:");
                    try {
                        double precio = Double.parseDouble(pStr);
                        int cant = Integer.parseInt(sStr);
                        // Expandir array de precios
                        double[] nuevosPrecios = new double[precios.length + 1];
                        for (int i = 0; i < precios.length; i++) {
                            nuevosPrecios[i] = precios[i];
                        }
                        nuevosPrecios[nuevosPrecios.length - 1] = precio;
                        precios = nuevosPrecios;

                        nombres.add(nombre);
                        stock.put(nombre, cant);
                        JOptionPane.showMessageDialog(null, "Producto agregado");
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Entrada invalida");
                    }
                    break;

                case 2: // Listar inventario
                    if (nombres.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Inventario vacio");
                    } else {
                        String lista = "Inventario:\n"; // usamos un String normal para mostrar el inventario en forma de lista
                        for (int i = 0; i < nombres.size(); i++) {
                            String nom = nombres.get(i);
                            lista += (i + 1) + ". " + nom
                                    + " | Precio: $" + precios[i]
                                    + " | Stock: " + stock.get(nom) + "\n";
                        }
                        JOptionPane.showMessageDialog(null, lista);
                    }
                    break;

                case 3: // Comprar producto
                    String prod = JOptionPane.showInputDialog("Ingrese el nombre del producto:");
                    if (prod == null || prod.trim().isEmpty())

                        break;
                    int idx = nombres.indexOf(prod);
                    if (idx == -1) {
                        JOptionPane.showMessageDialog(null, "Producto no encontrado");
                        break;
                    }
                    String cantStr = JOptionPane.showInputDialog("Cantidad a comprar:");
                    try {
                        int cantidad = Integer.parseInt(cantStr);
                        int disponible = stock.get(prod);
                        if (cantidad <= 0 || cantidad > disponible) {
                            JOptionPane.showMessageDialog(null, "Stock insuficiente");
                        } else {
                            double costo = precios[idx] * cantidad;
                            totalCompras += costo;
                            stock.put(prod, disponible - cantidad);
                            JOptionPane.showMessageDialog(null, "Compra realizada\nTotal: $" + costo);
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Entrada invalida");
                    }
                    break;

                case 4: // Estadisticas de productos y precios
                    if (nombres.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Inventario vacio");
                    } else {
                        double min = precios[0], max = precios[0];
                        String nomMin = nombres.get(0), nomMax = nombres.get(0);
                        for (int i = 1; i < precios.length; i++) {
                            if (precios[i] < min) {
                                min = precios[i];
                                nomMin = nombres.get(i);
                            }
                            if (precios[i] > max) {
                                max = precios[i];
                                nomMax = nombres.get(i);
                            }
                        }
                        JOptionPane.showMessageDialog(null, "Mas barato: " + nomMin + " $" + min + "\n"
                                + "Mas caro: " + nomMax + " $" + max);
                    }
                    break;

                case 5: // Buscar producto
                    String busq = JOptionPane.showInputDialog("Ingrese nombre o parte del nombre:");
                    if (busq == null || busq.trim().isEmpty())
                        break;

                    String res = "Resultados:\n"; // aqui se acumulan los resultados

                    for (int i = 0; i < nombres.size(); i++) {
                        if (nombres.get(i).toLowerCase().contains(busq.toLowerCase())) {
                            res += nombres.get(i)
                                    + " | Precio: $" + precios[i]
                                    + " | Stock: " + stock.get(nombres.get(i))
                                    + "\n";
                        }
                    }

                    JOptionPane.showMessageDialog(null, res);

                    break;

                case 0: // Salir
                    JOptionPane.showMessageDialog(null, "Gracias por su compra\nTotal gastado: $" + totalCompras);
                    break;

                default:// Opcion invalida
                    JOptionPane.showMessageDialog(null, "Opcion invalida");
            }

        } while (opcion != 0);
    }
}
