
package com.scs;

import com.scs.managers.CheckoutManager;
import com.scs.managers.DailyReportManager;
import com.scs.managers.InventoryManager;
import com.scs.managers.PaymentManager;
import com.scs.models.Item;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

    // Controllers
    private CheckoutManager checkoutManager = new CheckoutManager();

    private InventoryManager inventoryManager = new InventoryManager();

    private DailyReportManager dailyReportManager = new DailyReportManager();

    // Checkout UI
    private ObservableList<Item> checkoutData = FXCollections.observableArrayList();
    private TableView<Item> checkoutTable = new TableView(checkoutData);

    private Label subTotalLabel = new Label("SUBTOTAL $0");
    private Float subTotal = new Float(0.0f);

    private Label totalLabel = new Label("TOTAL $0");
    private Float total = new Float(0.0f);

    private Label promptLabel = new Label("Please scan items");

    // Receipt Holder
    private Label receiptLabel = new Label("Receipt");

    // Report Holder
    private Label reportLabel = new Label("Report");

    // Buffers
    private Item itemBuff;
    private Float cashBuff;
    private Float changeBuff;

    // Sub-stages
    private Stage ageStage;
    private Stage paymentStage;
    private Stage cashStage;
    private Stage creditStage;
    private Stage debitStage;
    private Stage acceptStage;
    private Stage addItemStage;

    // Inventory UI
    private ObservableList<Item> inventoryData = FXCollections.observableArrayList();
    private TableView<Item> inventoryTable = new TableView(inventoryData);

    private final TextField nameField = new TextField();
    private final TextField descriptionField = new TextField();
    private final TextField priceField = new TextField();
    private final TextField discountField = new TextField();
    private final TextField quantityField = new TextField();
    private final TextField inventoryLevelField = new TextField();

    private Button modifyBtn = new Button("Add");

    @Override
    public void start(Stage stage) {

        ageStage(stage);
        paymentStage(stage);
        cashStage(stage);
        creditStage(stage);
        debitStage(stage);
        acceptStage(stage);
        addItemStage(stage);

        Tab checkoutTab = new Tab("Checkout");
        checkoutTab.setContent(checkoutPane());

        Tab inventoryTab = new Tab("Inventory");
        inventoryTab.setContent(inventoryPane());

        HBox receiptHBOX = new HBox(10);
        receiptHBOX.setPadding(new Insets(10));
        receiptHBOX.getChildren().addAll(receiptLabel);

        Tab receiptTab = new Tab("Receipt");
        receiptTab.setContent(receiptHBOX);

        Button revenueBtn = new Button("Revenue");
        revenueBtn.setOnAction(event -> {
            reportLabel.setText(dailyReportManager.printRevenueReport());
        });
        Button inventoryBtn = new Button("Print Inventory");
        inventoryBtn.setOnAction(event -> {
            reportLabel.setText(dailyReportManager.printInventoryReport());
        });

        HBox reportHBOX = new HBox(10);
        reportHBOX.setPadding(new Insets(10));
        reportHBOX.setAlignment(Pos.CENTER);
        reportHBOX.getChildren().addAll(revenueBtn, inventoryBtn);

        VBox reportVBOX = new VBox(10);
        reportVBOX.setPadding(new Insets(10));
        reportVBOX.getChildren().addAll(reportHBOX, reportLabel);

        Tab reportTab = new Tab("Report");
        reportTab.setContent(reportVBOX);

        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(checkoutTab, inventoryTab, receiptTab, reportTab);
        tabPane.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) -> {
//                    System.out.println(newValue.getText());
                    if (newValue == inventoryTab) {
                        inventoryData.setAll(inventoryManager.getInventory());
//                        System.out.println("Tab");
                    }
                }
        );

        Scene scene = new Scene(tabPane);
        stage.setScene(scene);
        stage.setTitle("Self-Checkout System");
        stage.show();
    }

    private void subTotal() {
        this.subTotal = 0.0f;
        this.checkoutData.forEach(item -> {
            this.subTotal += item.getPrice();
        });

        this.subTotalLabel.setText(String.format("SUBTOTAL $%f", subTotal));
    }

    private void total() {
        this.total = 0.0f;
        this.checkoutData.forEach(item -> {
            this.total += item.getPrice();
        });

        this.totalLabel.setText(String.format("TOTAL $%f", total));
    }

    private void resetCheckout() {
        checkoutData.clear();
        subTotalLabel.setText("SUBTOTAL $0");
        totalLabel.setText("TOTAL $0");
        promptLabel.setText("Please scan items");
        checkoutManager.resetItems();
    }

    private void resetItem() {
        nameField.clear();
        descriptionField.clear();
        priceField.clear();
        discountField.clear();
        quantityField.clear();
        inventoryLevelField.clear();
    }

    private void setItem(Item item) {
        nameField.setText(item.getName());
        descriptionField.setText(item.getDescription());
        priceField.setText(String.valueOf(item.getPrice()));
        discountField.setText(String.valueOf(item.getDiscount()));
        quantityField.setText(String.valueOf(item.getQuantity()));
        inventoryLevelField.setText(String.valueOf(item.getInventoryLevel()));
    }

    private void updateReceipt(String receipt) {
        receiptLabel.setText(receipt);
    }

    private VBox checkoutPane() {
        TableColumn nameCol = new TableColumn("name");
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );
        TableColumn descriptionCol = new TableColumn("description");
        descriptionCol.setCellValueFactory(
                new PropertyValueFactory<>("description")
        );
        TableColumn priceCol = new TableColumn("price");
        priceCol.setCellValueFactory(
                new PropertyValueFactory<>("price")
        );
        checkoutTable.getColumns().addAll(nameCol, descriptionCol, priceCol);

        subTotalLabel.setFont(new Font(20));
        totalLabel.setFont(new Font(20));

        Button scanBtn = new Button("Scan");
        scanBtn.setOnAction((event -> {
            Item item = this.checkoutManager.scan(0);
            if (item.getName().equals("Alcohol")) {
                itemBuff = item;
                ageStage.show();
            } else {
                this.checkoutData.add(item);
            }
        }));

        Button subTotalBtn = new Button("SUBTOTAL");
        subTotalBtn.setOnAction((event -> {
            subTotal();
        }));

        Button totalBtn = new Button("TOTAL");
        totalBtn.setOnAction(event -> {
            total();
        });

        Button payBtn = new Button("PAY");
        payBtn.setOnAction(event -> {
            total();
            this.paymentStage.show();
        });

        Button cancelCheckoutBtn = new Button("CANCEL CHECKOUT");
        cancelCheckoutBtn.setOnAction(event -> {
            resetCheckout();
        });

        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(10));
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(scanBtn, subTotalBtn, totalBtn, payBtn, cancelCheckoutBtn);

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.setAlignment(Pos.TOP_LEFT);
        vBox.getChildren().addAll(checkoutTable, subTotalLabel, totalLabel, hBox, promptLabel);

        return vBox;
    }

    private void ageStage(Stage owner) {

        this.ageStage = new Stage();

        Label ageLabel = new Label("Input your age");
        final TextField ageField = new TextField();

        Button confirmBtn = new Button("Confirm");
        confirmBtn.setOnAction(event -> {
            if (checkoutManager.validateAge(Integer.valueOf(ageField.getText()))) {
                this.checkoutData.add(itemBuff);
                this.promptLabel.setText("You are eligible to bug alcohol");
            } else {
                this.promptLabel.setText("You are not eligible to bug alcohol");
            }

            ageField.clear();
            ageStage.close();
        });

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(ageLabel, ageField, confirmBtn);

        Scene scene = new Scene(vBox);
        ageStage.setScene(scene);
        ageStage.initOwner(owner);
        ageStage.initModality(Modality.APPLICATION_MODAL);
    }

    private void paymentStage(Stage owner) {

        this.paymentStage = new Stage();

        Button cashBtn = new Button("CASH");
        cashBtn.setOnAction(event -> {
            cashStage.show();
            paymentStage.close();
        });
        Button creditBtn = new Button("CREDIT");
        creditBtn.setOnAction(event -> {
            creditStage.show();
            paymentStage.close();
        });
        Button debitBtn = new Button("DEBIT");
        debitBtn.setOnAction(event -> {
            debitStage.show();
            paymentStage.close();
        });
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(event -> {
            paymentStage.close();
            resetCheckout();
        });

        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(10));
        hBox.getChildren().addAll(cashBtn, creditBtn, debitBtn, cancelBtn);

        Scene scene = new Scene(hBox);
        this.paymentStage.setScene(scene);
        paymentStage.initOwner(owner);
        paymentStage.initModality(Modality.APPLICATION_MODAL);
    }

    private void cashStage(Stage owner) {
        cashStage = new Stage();

        Label cashLabel = new Label("Insert cash");
        final TextField cashField = new TextField();

        Button payBtn = new Button("Pay");
        payBtn.setOnAction(event -> {
            total();

            cashBuff = Float.valueOf(cashField.getText());
            final Float difference = checkoutManager.payWithCash(cashBuff, total);
            if (difference >= 0) {
                changeBuff = difference;
                updateReceipt(checkoutManager.generateReceipt(PaymentManager.kCASH, "", total, cashBuff, changeBuff));
                resetCheckout();
            } else {
                promptLabel.setText("#Insufficient Cash");
                paymentStage.show();
            }
            cashField.clear();
            cashStage.close();
        });

        Button cancelBtn = new Button("CANCEL PAYMENT");
        cancelBtn.setOnAction(event -> {
            paymentStage.show();
            cashStage.close();
        });

        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(10));
        hBox.getChildren().addAll(payBtn, cancelBtn);

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(cashLabel, cashField, hBox);

        Scene scene = new Scene(vBox);
        cashStage.setScene(scene);
        cashStage.initOwner(owner);
        cashStage.initModality(Modality.APPLICATION_MODAL);
    }

    private void creditStage(Stage owner) {
        creditStage = new Stage();

        Label cardNumLabel = new Label("Card Number");
        final TextField cardNumField = new TextField();

        Button payBtn = new Button("Pay");
        payBtn.setOnAction(event -> {
            if (checkoutManager.payWithCreditCard(cardNumField.getText())) {
                acceptStage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Card is rejected", ButtonType.OK);
                alert.showAndWait();
                resetCheckout();
            }
            creditStage.close();
        });

        Button cancelBtn = new Button("CANCEL PAYMENT");
        cancelBtn.setOnAction(event -> {
            paymentStage.show();
            creditStage.close();
        });

        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(10));
        hBox.getChildren().addAll(payBtn, cancelBtn);

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(cardNumLabel, cardNumField, hBox);

        Scene scene = new Scene(vBox);
        creditStage.setScene(scene);
        creditStage.initOwner(owner);
        creditStage.initModality(Modality.APPLICATION_MODAL);
    }

    private void debitStage(Stage owner) {
        debitStage = new Stage();

        Label cardNumLabel = new Label("Card Number");
        final TextField cardNumField = new TextField();

        Label pinLabel = new Label("PIN");
        final PasswordField pinField = new PasswordField();

        Button payBtn = new Button("Pay");
        payBtn.setOnAction(event -> {
            if (checkoutManager.payWithDebitCard(cardNumField.getText(), pinField.getText())) {
                acceptStage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Card is rejected", ButtonType.OK);
                alert.showAndWait();
                resetCheckout();
            }
            debitStage.close();
        });

        Button cancelBtn = new Button("CANCEL PAYMENT");
        cancelBtn.setOnAction(event -> {
            paymentStage.show();
            debitStage.close();
        });

        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(10));
        hBox.getChildren().addAll(payBtn, cancelBtn);

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(cardNumLabel, cardNumField, pinLabel, pinField, hBox);

        Scene scene = new Scene(vBox);
        debitStage.setScene(scene);
        debitStage.initOwner(owner);
        debitStage.initModality(Modality.APPLICATION_MODAL);
    }

    private void acceptStage(Stage owner) {
        this.acceptStage = new Stage();

        Label signLabel = new Label("Please sign your name");
        final TextField signField = new TextField();

        Button confirmBtn = new Button("Confirm");
        confirmBtn.setOnAction(event -> {
            updateReceipt(checkoutManager.generateReceipt(PaymentManager.kCARD, signField.getText(), total, 0.0f, 0.0f));

            signField.clear();
            acceptStage.close();
            resetCheckout();
        });

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(signLabel, signField, confirmBtn);

        Scene scene = new Scene(vBox);
        acceptStage.setScene(scene);
        acceptStage.initOwner(owner);
        acceptStage.initModality(Modality.APPLICATION_MODAL);
    }


    private VBox inventoryPane() {

        TableColumn nameCol = new TableColumn("name");
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );
        TableColumn descriptionCol = new TableColumn("description");
        descriptionCol.setCellValueFactory(
                new PropertyValueFactory<>("description")
        );
        TableColumn priceCol = new TableColumn("price");
        priceCol.setCellValueFactory(
                new PropertyValueFactory<>("price")
        );
        TableColumn discountCol = new TableColumn("discount");
        discountCol.setCellValueFactory(
                new PropertyValueFactory<>("discount")
        );
        TableColumn quantityCol = new TableColumn("quantity");
        quantityCol.setCellValueFactory(
                new PropertyValueFactory<>("quantity")
        );
        TableColumn inventoryLevelCol = new TableColumn("inventoryLevel");
        inventoryLevelCol.setCellValueFactory(
                new PropertyValueFactory<>("inventoryLevel")
        );

        inventoryTable.getColumns().addAll(nameCol, descriptionCol, priceCol, discountCol, quantityCol, inventoryLevelCol);

        final TextField _nameField = new TextField();
        final TextField _quantityField = new TextField();
        Button scanBtn = new Button("Scan");
        scanBtn.setOnAction(event -> {
            String name = _nameField.getText();
            Integer quantity = Integer.valueOf(_quantityField.getText());
            if (inventoryManager.updateQuantity(name, quantity)) {
                nameField.clear();
                quantityField.clear();
                inventoryData.setAll(inventoryManager.getInventory());
            } else {
//                System.out.println("SCAN");
                nameField.setText(name);
                quantityField.setText(String.valueOf(quantity));
                modifyBtn.setText("ADD");
                addItemStage.show();
            }
            _nameField.clear();
            _quantityField.clear();
        });

        HBox scanHBox = new HBox(10);
        scanHBox.setPadding(new Insets(10));
        scanHBox.setAlignment(Pos.CENTER);
        scanHBox.getChildren().addAll(_nameField, _quantityField, scanBtn);

        Button addBtn = new Button("ADD");
        addBtn.setOnAction(event -> {
            resetItem();
            modifyBtn.setText("ADD");
            addItemStage.show();
        });

        Button updateBtn = new Button("UPDATE");
        updateBtn.setOnAction(event -> {
            Integer index = inventoryTable.getSelectionModel().getSelectedIndex();
            Item item = inventoryManager.findItem(index);
            setItem(item);
            modifyBtn.setText("UPDATE");
            addItemStage.show();
        });

        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(10));
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(addBtn, updateBtn);

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.setAlignment(Pos.TOP_LEFT);
        vBox.getChildren().addAll(inventoryTable, scanHBox, hBox);

        return vBox;
    }

    private void addItemStage(Stage owner) {
        addItemStage = new Stage();

        Label nameLabel = new Label("Name");
        Label descriptionLabel = new Label("Description");
        Label priceLabel = new Label("Price");
        Label discountLabel = new Label("Discount");
        Label quantityLabel = new Label("Quantity");
        Label inventoryLevelLabel = new Label("InventoryLevel");

        modifyBtn.setOnAction(event -> {
            Item newItem = new Item(nameField.getText(), descriptionField.getText(), Float.valueOf(priceField.getText()), Float.valueOf(discountField.getText()), Integer.valueOf(quantityField.getText()), Integer.valueOf(inventoryLevelField.getText()));
            if (modifyBtn.getText().equals("ADD")) {
                inventoryManager.append(newItem);
                inventoryData.setAll(inventoryManager.getInventory());
            } else if (modifyBtn.getText().equals("UPDATE")) {
                inventoryManager.update(newItem);
                inventoryData.setAll(inventoryManager.getInventory());
            }

            resetItem();
            addItemStage.close();
        });
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(event -> {
            resetItem();
            addItemStage.close();
        });

        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(10));
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(modifyBtn, cancelBtn);

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(nameLabel, nameField, descriptionLabel, descriptionField, priceLabel, priceField, discountLabel, discountField, quantityLabel, quantityField, inventoryLevelLabel, inventoryLevelField, hBox);

        Scene scene = new Scene(vBox);
        addItemStage.setScene(scene);
        addItemStage.initOwner(owner);
        addItemStage.initModality(Modality.APPLICATION_MODAL);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
