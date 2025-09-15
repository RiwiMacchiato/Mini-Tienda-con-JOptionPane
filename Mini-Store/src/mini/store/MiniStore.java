package mini.store;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.HashMap;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author ingfr
 */
public class MiniStore {
    
    // find product by name
    private static int indexOfNombre(ArrayList<String> nombres, String nombre) {
        for (int i = 0; i < nombres.size(); i++) {
            if (nombres.get(i).equalsIgnoreCase(nombre)) {
                return i;
            }
        }
        return -1;
    }

    // add new product
    private static void addProducto(ArrayList<String> nombres, double[] precios,
            HashMap<String, Integer> stock, int contador, String nombre, double precio,
            int cantidad) {
        nombres.add(nombre);
        precios[contador] = precio;
        stock.put(nombre, cantidad);
    }

    // expand prices array when full
    private static double[] expandPrecios(double[] precios) {
        double[] nuevo = new double[precios.length * 2];
        for (int i = 0; i < precios.length; i++) {
            nuevo[i] = precios[i];
        }
        return nuevo;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        ArrayList<String> productNames = new ArrayList<>();
        double[] productPrices = new double[100];
        HashMap<String, Integer> productStock = new HashMap<>();
        int productCount = 0;
        double totalSales = 0.0;
        boolean runProgram = true;

        while (runProgram) {
            // Reset variables for new execution
            productNames.clear();
            productStock.clear();
            for (int i = 0; i < productPrices.length; i++) {
                productPrices[i] = 0.0;
            }
            productCount = 0;
            totalSales = 0.0;

            // Welcome message
            JOptionPane.showMessageDialog(null,
                    "Bienvenido al Sistema de Mini-Tienda!\nGestiona tu inventario de productos facilmente.",
                    "Mini-Tienda", JOptionPane.INFORMATION_MESSAGE);

            boolean continueSession = true; // Main menu control

            while (continueSession) {
                // Show options menu
                String menu = "=== MINI-TIENDA - MENU PRINCIPAL ===\n\n" + "1. Agregar producto\n"
                        + "2. Listar inventario\n" + "3. Comprar producto\n"
                        + "4. Mostrar estadisticas\n" + "5. Buscar producto\n" + "6. Salir\n\n"
                        + "Seleccione una opcion:";

                String option = JOptionPane.showInputDialog(null, menu, "Mini-Tienda",
                        JOptionPane.QUESTION_MESSAGE);

                // Validate if user cancelled
                if (option == null) {
                    continueSession = false;
                    continue;
                }

                // Validate if input is numeric
                if (!option.trim().matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "Por favor ingrese un numero valido.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                int choice = Integer.parseInt(option.trim());

                // Switch to handle menu options
                switch (choice) {
                    case 1: // Add product
                        // Request product name
                        String name =
                                JOptionPane.showInputDialog(null, "Ingrese el nombre del producto:",
                                        "Agregar Producto", JOptionPane.QUESTION_MESSAGE);

                        if (name == null || name.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "El nombre no puede estar vacio.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            break;
                        }

                        name = name.trim();

                        // Check if product already exists
                        int existingIndex = indexOfNombre(productNames, name);
                        boolean exists = (existingIndex != -1);

                        if (exists) {
                            JOptionPane.showMessageDialog(null, "El producto ya existe.", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            break;
                        }

                        // Request price
                        String priceStr =
                                JOptionPane.showInputDialog(null, "Ingrese el precio del producto:",
                                        "Agregar Producto", JOptionPane.QUESTION_MESSAGE);

                        if (priceStr == null || priceStr.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "El precio no puede estar vacio.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            break;
                        }

                        if (!priceStr.trim().matches("\\d+(\\.\\d+)?")) {
                            JOptionPane.showMessageDialog(null,
                                    "Ingrese un precio numerico valido.", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            break;
                        }

                        double price = Double.parseDouble(priceStr.trim());

                        if (price < 0) {
                            JOptionPane.showMessageDialog(null, "El precio no puede ser negativo.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            break;
                        }

                        // Request stock
                        String stockStr =
                                JOptionPane.showInputDialog(null, "Ingrese la cantidad en stock:",
                                        "Agregar Producto", JOptionPane.QUESTION_MESSAGE);

                        if (stockStr == null || stockStr.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "El stock no puede estar vacio.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            break;
                        }

                        if (!stockStr.trim().matches("\\d+")) {
                            JOptionPane.showMessageDialog(null, "Ingrese una cantidad valida.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            break;
                        }

                        int stock = Integer.parseInt(stockStr.trim());

                        if (stock < 0) {
                            JOptionPane.showMessageDialog(null, "El stock no puede ser negativo.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            break;
                        }

                        // Check if need to expand prices array
                        if (productCount >= productPrices.length) {
                            productPrices = expandPrecios(productPrices);
                        }

                        // Add product using utility method
                        addProducto(productNames, productPrices, productStock, productCount, name,
                                price, stock);
                        productCount++;

                        JOptionPane.showMessageDialog(null,
                                "Producto agregado exitosamente:\n" + "Nombre: " + name + "\n"
                                        + "Precio: $" + String.format("%.2f", price) + "\n"
                                        + "Stock: " + stock + " unidades",
                                "Exito", JOptionPane.INFORMATION_MESSAGE);
                        break;

                    case 2: // List inventory
                        if (productNames.isEmpty()) {
                            JOptionPane.showMessageDialog(null,
                                    "No hay productos en el inventario.", "Inventario Vacio",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            String inventory = "=== INVENTARIO DE PRODUCTOS ===\n\n";

                            for (int i = 0; i < productNames.size(); i++) {
                                String productName = productNames.get(i);
                                double productPrice = productPrices[i];
                                int productStockAmount = productStock.get(productName);

                                inventory += String.format("%d. %s\n", (i + 1), productName);
                                inventory += String.format("   Precio: $%.2f\n", productPrice);
                                inventory += String.format("   Stock: %d unidades\n\n",
                                        productStockAmount);
                            }

                            JOptionPane.showMessageDialog(null, inventory, "Inventario",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                        break;

                    case 3: // Buy product
                        if (productNames.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "No hay productos disponibles.",
                                    "Sin Productos", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            // Request product name
                            String productName = JOptionPane.showInputDialog(null,
                                    "Ingrese el nombre del producto que desea comprar:",
                                    "Comprar Producto", JOptionPane.QUESTION_MESSAGE);

                            if (productName == null) {
                                JOptionPane.showMessageDialog(null, "Operacion cancelada",
                                        "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            }

                            if (productName.trim().length() < 2) {
                                JOptionPane.showMessageDialog(null,
                                        "El nombre debe tener al menos 2 caracteres", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                                break;
                            }

                            productName = productName.trim();

                            // Search for the product using utility method
                            int productIndex = indexOfNombre(productNames, productName);

                            if (productIndex == -1) {
                                JOptionPane.showMessageDialog(null, "El producto no existe.",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                break;
                            }

                            // Check stock
                            String actualName = productNames.get(productIndex);
                            int availableStock = productStock.get(actualName);

                            if (availableStock == 0) {
                                JOptionPane.showMessageDialog(null,
                                        "El producto no tiene stock disponible.", "Sin Stock",
                                        JOptionPane.ERROR_MESSAGE);
                                break;
                            }

                            // Request quantity
                            String quantityStr = JOptionPane.showInputDialog(null,
                                    "Producto: " + actualName + "\n" + "Precio: $"
                                            + String.format("%.2f", productPrices[productIndex])
                                            + "\n" + "Stock disponible: " + availableStock + "\n\n"
                                            + "Ingrese la cantidad que desea comprar:",
                                    "Comprar Producto", JOptionPane.QUESTION_MESSAGE);

                            if (quantityStr == null || quantityStr.trim().isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Debe ingresar una cantidad.",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                break;
                            }

                            if (!quantityStr.trim().matches("\\d+")) {
                                JOptionPane.showMessageDialog(null,
                                        "Ingrese una cantidad numerica valida.", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                                break;
                            }

                            int quantity = Integer.parseInt(quantityStr.trim());

                            if (quantity <= 0) {
                                JOptionPane.showMessageDialog(null,
                                        "La cantidad debe ser mayor a cero.", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                                break;
                            }

                            if (quantity > availableStock) {
                                JOptionPane.showMessageDialog(null,
                                        "No hay suficiente stock. Solo hay " + availableStock
                                                + " unidades.",
                                        "Stock Insuficiente", JOptionPane.ERROR_MESSAGE);
                                break;
                            }

                            // Calculate total
                            double unitPrice = productPrices[productIndex];
                            double totalPrice = unitPrice * quantity;

                            // Confirm purchase
                            int confirm = JOptionPane.showConfirmDialog(null,
                                    "=== CONFIRMAR COMPRA ===\n\n" + "Producto: " + actualName
                                            + "\n" + "Cantidad: " + quantity + " unidades\n"
                                            + "Precio unitario: $"
                                            + String.format("%.2f", unitPrice) + "\n"
                                            + "Total a pagar: $" + String.format("%.2f", totalPrice)
                                            + "\n\n" + "Confirma la compra?",
                                    "Confirmar Compra", JOptionPane.YES_NO_OPTION);

                            if (confirm == JOptionPane.YES_OPTION) {
                                // Update stock and sales
                                productStock.put(actualName, availableStock - quantity);
                                totalSales += totalPrice;

                                JOptionPane.showMessageDialog(null,
                                        "Compra realizada exitosamente!\n\n" + "Producto: "
                                                + actualName + "\n" + "Cantidad: " + quantity
                                                + " unidades\n" + "Total pagado: $"
                                                + String.format("%.2f", totalPrice) + "\n"
                                                + "Stock restante: " + (availableStock - quantity),
                                        "Compra Exitosa", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                        break;

                    case 4: // Show statistics
                        if (productNames.isEmpty()) {
                            JOptionPane.showMessageDialog(null,
                                    "No hay productos para mostrar estadisticas.", "Sin Datos",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            // Find cheapest and most expensive product
                            double minPrice = productPrices[0];
                            double maxPrice = productPrices[0];
                            String cheapestProduct = productNames.get(0);
                            String expensiveProduct = productNames.get(0);

                            for (int i = 1; i < productNames.size(); i++) {
                                if (productPrices[i] < minPrice) {
                                    minPrice = productPrices[i];
                                    cheapestProduct = productNames.get(i);
                                }
                                if (productPrices[i] > maxPrice) {
                                    maxPrice = productPrices[i];
                                    expensiveProduct = productNames.get(i);
                                }
                            }

                            // Calculate average price
                            double totalPrices = 0;
                            for (int i = 0; i < productNames.size(); i++) {
                                totalPrices += productPrices[i];
                            }
                            double averagePrice = totalPrices / productNames.size();

                            String statistics = "=== ESTADISTICAS ===\n\n" + "Total de productos: "
                                    + productNames.size() + "\n\n" + "Producto mas barato:\n"
                                    + cheapestProduct + " - $" + String.format("%.2f", minPrice)
                                    + "\n\n" + "Producto mas caro:\n" + expensiveProduct + " - $"
                                    + String.format("%.2f", maxPrice) + "\n\n"
                                    + "Precio promedio: $" + String.format("%.2f", averagePrice)
                                    + "\n\n" + "Total de ventas: $"
                                    + String.format("%.2f", totalSales);

                            JOptionPane.showMessageDialog(null, statistics, "Estadisticas",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                        break;

                    case 5: // Search product
                        if (productNames.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "No hay productos para buscar.",
                                    "Sin Productos", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            String searchTerm = JOptionPane.showInputDialog(null,
                                    "Ingrese el nombre o parte del nombre del producto:",
                                    "Buscar Producto", JOptionPane.QUESTION_MESSAGE);

                            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                                JOptionPane.showMessageDialog(null,
                                        "Debe ingresar un termino de busqueda.", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                                break;
                            }

                            searchTerm = searchTerm.trim().toLowerCase();
                            String results = "=== RESULTADOS DE BUSQUEDA ===\n\n";
                            boolean found = false;

                            for (int i = 0; i < productNames.size(); i++) {
                                String currentProduct = productNames.get(i);
                                if (currentProduct.toLowerCase().contains(searchTerm)) {
                                    found = true;
                                    double productPrice = productPrices[i];
                                    int productStockAmount = productStock.get(currentProduct);

                                    results += currentProduct + "\n";
                                    results += "Precio: $" + String.format("%.2f", productPrice)
                                            + "\n";
                                    results += "Stock: " + productStockAmount + " unidades\n\n";
                                }
                            }

                            if (found) {
                                JOptionPane.showMessageDialog(null, results, "Resultados",
                                        JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "No se encontraron productos.",
                                        "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                        break;

                    case 6: // Exit
                        continueSession = false;

                        // Show final ticket
                        String ticket = "=== TICKET FINAL ===\n\n" + "Productos registrados: "
                                + productNames.size() + "\n" + "Total de ventas: $"
                                + String.format("%.2f", totalSales) + "\n\n";

                        if (totalSales > 0) {
                            ticket += "Gracias por sus compras!";
                        } else {
                            ticket += "No se realizaron ventas.";
                        }

                        JOptionPane.showMessageDialog(null, ticket, "Ticket Final",
                                JOptionPane.INFORMATION_MESSAGE);
                        break;

                    default:
                        JOptionPane.showMessageDialog(null,
                                "Opcion invalida. Seleccione del 1 al 6.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                }
            }
            runProgram = false;
        }

        // Final goodbye message
        JOptionPane.showMessageDialog(null, "Gracias por usar Mini-Tienda!\nHasta la proxima!",
                "Despedida", JOptionPane.INFORMATION_MESSAGE);
    }
}
