package com.michtech.pointofSale.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.michtech.pointofSale.Ui.History;
import com.michtech.pointofSale.background.CalculateData;
import com.michtech.pointofSale.list.ProductList;
import com.michtech.pointofSale.pojo.PojoGetEmployee;
import com.michtech.pointofSale.pojo.PojoPaymentHistory;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseManager extends SQLiteOpenHelper {
    SQLiteDatabase db;

    private static final String DatabaseName = "stores";
    private static final int Version = 1;
    private static final String TableStores = "stores";
    private static final String TableProducts = "products";
    private static final String TableCategory = "category";
    private static final String TablePrice = "price";
    private static final String id = "id";
    private static final String StoreName = "StoreName";
    private static final String ProductName = "ProductName";
    private static final String Description = "Description";
    private static final String Amount = "Amount";
    private static final String CategoryID = "CategoryID";
    private static final String PriceID = "PriceID";
    private static final String Code  = "Code";
    private static final String Category = "Category";
    private static final String DDate = "DDate";
    private static final String ProductImage = "ProductImage";
    private static final String PurchasePrice = "PurchasePrice";
    private static final String SellingPrice = "SellingPrice";
    private static final String TableEmployee = "TableEmployee";
    private static final String Name = "Name";
    private static final String Email = "Email";
    private static final String Phone = "Phone";
    private static final String Bank = "Bank";
    private static final String Paid = "Paid";
    private static final String TableHistoryProducts = "productsHistory";
    private static final String PurchaseOrSold = "PurchaseOrSold";
    private static final String TempProductTable = "temp_product_tale";
    private static final String ProductId = "ProductId";
    private static final String Price = "Price";
    private static final String PriceSold = "PriceSold";
    private static final String Type = "Type";
    private static final String TableOtherExpense = "other_expense";
    private static final String ImageCode = "ImageCode";
    private static final String TableTotalSales = "total_sales";
    private static final String TotalSales = "TotalSales";
    private static final String Image = "Image";
    private static final String TableUser = "table_user";
    private static final String User = "User";
    private static final String Password = "Password";
    private static final String AccountType = "AccountType";
    private static final String TotalPurchases = "TotalPurchases";
    private static final String TableTotalPurchases = "total_purchases";
    private static final String TablePaymentHistory = "payment_history";
    private static final String StartDate = "StartDate";
    private static final String EndDate = "EndDate";
    private static final String TableDevices = "devices";
    private static final String DeviceUserName="DeviceUserName";
    private static final String DevicePassword = "Password";
    private static final String CustomOrEmployee = "custom_or_employee";
    private static final String Reason = "Reason";
    private static final String TableRemovedProducts = "removed_products";
    private static final String TableExpiryDate = "expiry_date";
    private static final String ExpiryDate = "ExpiryDate";
    private static final String TablePaymentMethod = "payment_method";
    private static final String PaymentMethod = "PaymentMethod";
    private static final String HistoryId = "HistoryId";
    private static final String TablePaymentMethodLink = "payment_method_link";
    private static final String PaymentMethodId = "PaymentMethodId";
    private static final String TableProfits = "profits";
    private static final String ProfitMade = "ProfitMade";
    private static final String EstimatedProfit = "EstimatedProfit";
    private static final String SecurityQuestion = "SecurityQuestion";
    private static final String SecurityQuestionTable = "security_question_table";
    Context context;
    public DatabaseManager(Context context) {
        super(context, DatabaseName, null, Version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void CheckTables(){
        db = this.getReadableDatabase();
        Cursor cursor = null;
        try{
            cursor = db.query(TableCategory, null, null, null, null, null, null);
        }catch (Exception e){
            CreateTableCategory();
        }
        try{
            cursor = db.query(TablePrice, null, null, null, null, null, null);
        }catch (Exception e){
            CreateTablePrices();
        }
        try{
            cursor = db.query(TableProducts, null, null, null, null, null, null);
        }catch (Exception e){
            CreateTableProducts();
        }
        try{
            cursor = db.query(TableEmployee, null, null, null, null, null, null);
        }catch (Exception e){
            CreateTableEmployee();
        }
        try{
            cursor = db.query(TableHistoryProducts, null, null, null, null, null, null);
        }catch (Exception e){
            CreateTableHistory();
        }
        try{
            cursor = db.query(TableOtherExpense, null, null, null, null, null, null);
        }catch (Exception e){
            createTableOtherExpense();
        }
        try{
            cursor = db.query(TableTotalSales, null, null, null, null, null, null);
        }catch (Exception e){
            createTableTotalSales();
        }
        try{
            cursor = db.query(TableUser, null, null, null, null, null, null);
        }catch (Exception e){
            createTableUser();
        }
        try{
            cursor = db.query(TableStores, null, null, null, null, null, null);
        }catch (Exception e){
            CreateTableStore();
        }
        try{
            cursor = db.query(TableTotalPurchases, null, null, null, null, null, null);
        }catch (Exception e){
            CreateTableTotalPurchases();
        }
        try{
            cursor = db.query(TablePaymentHistory, null, null, null, null, null, null);
        }catch (Exception e){
            createTablePaymentHistory();
        }
        try{
            cursor = db.query(TableDevices, null, null, null, null, null, null);
        }catch (Exception e){
            createTableDevices();
        }
        try{
            cursor = db.query(TableRemovedProducts, null, null, null, null, null, null);
        }catch (Exception e){
            createTableRemovedProducts();
        }
        try{
            cursor = db.query(TableExpiryDate, null, null, null, null, null, null);
        }catch (Exception e){
            createTableExpiryDate();
        }
        try{
            cursor = db.query(TablePaymentMethod, null, null, null, null, null, null);
        }catch (Exception e){
            createTablePaymentMethod();
        }
        try{
            cursor = db.query(TablePaymentMethodLink, null, null, null, null, null, null);
        }catch (Exception e){
            createTablePaymentMethodLink();
        }
        try{
            cursor = db.query(TableProfits, null, null, null, null, null, null);
        }catch (Exception e){
            createTableProfits();
        }
        try{
            cursor = db.query(SecurityQuestionTable, null, null, null, null, null, null);
        }catch (Exception e){
            createSecurityQuestionTable();
        }
    }
    public String getDatabaseLocation(){
        db = this.getWritableDatabase();
        String path = context.getDatabasePath(DatabaseName).getPath();
        return path;
    }
    /*
    Products
     */
    public int CheckProducts(){
        db = this.getReadableDatabase();
        int n = 0;
        String query = "SELECT COUNT() FROM "+TableProducts;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            n = cursor.getInt(0);
        }
        return n;
    }
    public boolean checkCategory(String category){
        db = this.getReadableDatabase();
        boolean found = false;
        String query = "SELECT COUNT(id) AS found FROM "+TableCategory+" WHERE "+Category+"='"+category+"'";
        Cursor cursor  = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            if(cursor.getInt(0)>0){
                found = true;
            }
        }
        return found;
    }
    public void AddCategory(String category){
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(Category, category);
        db.insert(TableCategory, null, values);
    }
    public int getProductCategoryId(int Id){
        db = this.getReadableDatabase();
        int categoryId = 0;
        String query = "SELECT "+CategoryID+" FROM "+TableProducts+" WHERE "+id+"="+Id;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            categoryId = cursor.getInt(0);
        }
        return categoryId;
    }
    public String getCategory(int Id){
        db = this.getReadableDatabase();
        String category = "";
        String query = "SELECT "+Category+" FROM "+TableCategory+" WHERE "+id+"="+Id;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            category = cursor.getString(0);
        }
        return category;
    }
    public int getCurrentCategoryId(String category){
        db = this.getReadableDatabase();
        int n = 0;
        String query = "SELECT id FROM "+TableCategory+" WHERE "+Category+"="+"'"+category+"'"+" ORDER BY id DESC";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            n = cursor.getInt(0);
        }
        return n;
    }
    public void AddPrice(int purchasePrice, int sellingPrice){
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(PurchasePrice, purchasePrice);
        values.put(SellingPrice, sellingPrice);
        db.insert(TablePrice, null, values);
    }
    public void updatePrice(int purchasePrice, int sellingPrice, int Id){
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(PurchasePrice, purchasePrice);
        values.put(SellingPrice, sellingPrice);
        db.update(TablePrice, values, id+"="+Id, null);
    }
    public int getCurrentPriceId(int purchasePrice, int sellingPrice){
        db = this.getReadableDatabase();
        int n = 0;
        String query = "SELECT id FROM "+TablePrice+" WHERE "+PurchasePrice+"="+purchasePrice+" AND "+SellingPrice+"="+sellingPrice+" ORDER BY id DESC";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            n = cursor.getInt(0);
        }
        return n;
    }
    public void AddProducts(String name, String description, int amount, int categoryId, int priceId, String code, String dDate, byte[] image){
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(ProductName, name);
        values.put(Description, description);
        values.put(Amount, amount);
        values.put(CategoryID, categoryId);
        values.put(PriceID, priceId);
        values.put(Code, code);
        values.put(DDate, dDate);
        values.put(ProductImage, image);
        db.insert(TableProducts, null, values);
    }
    public void UpdateProducts(String name, String description, int amount, int categoryId, int priceId, String code, String dDate, byte[] image, int Id){
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(ProductName, name);
        values.put(Description, description);
        values.put(Amount, amount);
        values.put(CategoryID, categoryId);
        values.put(PriceID, priceId);
        values.put(Code, code);
        values.put(ProductImage, image);
        db.update(TableProducts, values, id+"="+Id, null);
    }
    public int getProductAmount(int productId){
        db = this.getReadableDatabase();
        int amount = 0;
        String query = "SELECT "+Amount+" FROM "+TableProducts+" WHERE id="+productId;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            amount = cursor.getInt(0);
        }
        return amount;
    }
    public String getProductsAddDate(int Id){
        db = this.getReadableDatabase();
        String date = "00";
        String query = "SELECT "+DDate+" FROM "+TableProducts+" WHERE "+id+"="+Id;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            date = cursor.getString(0);
        }
        return date;
    }
    public String[] getProductNameCode(int Id){
        db = this.getReadableDatabase();
        String data[] = new String[]{null, null, null};
        String query = "SELECT "+ProductName+", "+Code+" FROM "+TableProducts+" WHERE "+id+"="+Id;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            data[0] = cursor.getString(0);
            data[1] = cursor.getString(1);
        }
        return data;
    }
    public void updateProductAmount(int amount, int productId){
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(Amount, amount);
        db.update(TableProducts, values, "id="+productId, null);
    }
    public void deleteProduct(int productId){
        db = this.getReadableDatabase();
        db.delete(TableProducts, "id="+productId, null);
    }
    public List<DbHelper> getProductList(){
        db = this.getReadableDatabase();
        String query = "SELECT "+id+", "+ProductName+", "+Amount+", "+CategoryID+", "+DDate+" FROM "+TableProducts+" ORDER BY id DESC";
        String query2 = null;
        Cursor cursor = db.rawQuery(query, null);
        Cursor cursor1;
        List<DbHelper> dbHelperList = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                DbHelper dbHelper = new DbHelper();
                dbHelper.setId(cursor.getInt(0));
                dbHelper.setProductsName(cursor.getString(1));
                dbHelper.setAmount(cursor.getInt(2));
                dbHelper.setDDate(cursor.getString(4));

                query2 = "SELECT "+Category+" FROM "+TableCategory+" WHERE id="+cursor.getInt(3);
                cursor1 = db.rawQuery(query2, null);
                if(cursor1.moveToFirst()){
                    dbHelper.setCategory(cursor1.getString(0));
                }

                dbHelperList.add(dbHelper);
            }
            while (cursor.moveToNext());
        }
        return dbHelperList;
    }
    public int getCategoryId(String category){
        db = this.getReadableDatabase();
        int Id = 0;
        String query = "SELECT "+id+" FROM "+TableCategory+" WHERE "+Category+"='"+category+"' ORDER BY id DESC";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            Id = cursor.getInt(0);
        }
        System.out.println("Id category: "+Id);
        return Id;
    }
    public List<DbHelper> getProductList(int categoryId){
        db = this.getReadableDatabase();
        String query = "SELECT "+id+", "+ProductName+", "+Amount+", "+CategoryID+", "+DDate+" FROM "+TableProducts+" WHERE "+CategoryID+"="+categoryId+" ORDER BY id DESC";
        String query2 = null;
        Cursor cursor = db.rawQuery(query, null);
        Cursor cursor1;
        List<DbHelper> dbHelperList = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                DbHelper dbHelper = new DbHelper();
                dbHelper.setId(cursor.getInt(0));
                dbHelper.setProductsName(cursor.getString(1));
                dbHelper.setAmount(cursor.getInt(2));
                dbHelper.setDDate(cursor.getString(4));

                query2 = "SELECT "+Category+" FROM "+TableCategory+" WHERE id="+cursor.getInt(3);
                cursor1 = db.rawQuery(query2, null);
                if(cursor1.moveToFirst()){
                    dbHelper.setCategory(cursor1.getString(0));
                }

                dbHelperList.add(dbHelper);
            }
            while (cursor.moveToNext());
        }
        return dbHelperList;
    }
    @SuppressLint("Recycle")
    public List<ProductList> getSpecificProducts(int position){
        db = this.getReadableDatabase();
        List<ProductList> list = new ArrayList<>();
        String query = "SELECT "+id+", "+ProductName+", "+Description+", "+Amount+", "+CategoryID+", "+Code+", "+DDate+", "+PriceID+" FROM "+TableProducts+" WHERE id="+position;
        Cursor cursor = db.rawQuery(query, null);
        Cursor cursor1=null, cursor2=null;
        String query1 = null, query2 = null;
        if(cursor.moveToFirst()){
            String category = null;
            int purchasePrice=0, sellingPrice=0;
            query1 = "SELECT "+Category+" FROM "+Category+" WHERE id="+cursor.getInt(4);
            cursor1 = db.rawQuery(query1, null);
            if(cursor1.moveToFirst()){
                category = cursor1.getString(0);
            }
            query2 = "SELECT "+PurchasePrice+", "+SellingPrice+" FROM "+TablePrice+" WHERE id="+cursor.getInt(7);
            cursor2 = db.rawQuery(query2, null);
            if(cursor2.moveToFirst()){
                purchasePrice = cursor2.getInt(0);
                sellingPrice = cursor2.getInt(1);
            }
            ProductList productList = new ProductList(cursor.getInt(0), category, cursor.getString(2), cursor.getString(1), cursor.getInt(3), cursor.getString(5), purchasePrice, sellingPrice, cursor.getString(6));
            list.add(productList);
        }
        return list;
    }
    public byte[] getProductImage(int position){
        db = this.getReadableDatabase();
        byte[] image = new byte[0];
        String query = "SELECT "+ProductImage+" FROM "+TableProducts+" WHERE id="+position;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            image = cursor.getBlob(0);
        }
        return image;
    }
    public List<String> getAllProductsName(){
        db = this.getReadableDatabase();
        String query = "SELECT "+ProductName+" FROM "+TableProducts;
        List<String> productList = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                productList.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        return productList;
    }
    public List<String> getAllCategoryNames(){
        db = this.getReadableDatabase();
        String query = "SELECT "+Category+" FROM "+TableCategory;
        List<String> category = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                category.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        return category.stream().distinct().sorted().collect(Collectors.toList());
    }
    public List<String> getAllProductDescriptions(String productName){
        db = this.getReadableDatabase();
        String query = "SELECT "+Description+" FROM "+TableProducts+" WHERE "+ProductName+"='"+productName+"'";
        List<String> descriptionList = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                descriptionList.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        return descriptionList;
    }
    public boolean checkDescriptionNumber(String productName){
        db = this.getReadableDatabase();
        boolean found = false;
        String query = "SELECT COUNT(id) AS found FROM "+TableProducts+" WHERE "+ProductName+"='"+productName+"'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            if(cursor.getInt(0)>1){
                found = true;
            }
        }
        return found;
    }
    public boolean checkProductName(String productName){
        db = this.getReadableDatabase();
        boolean found = false;
        String query = "SELECT COUNT(id) AS found FROM "+TableProducts+" WHERE "+ProductName+"='"+productName+"'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            if(cursor.getInt(0)>0){
                found = true;
            }
        }
        return found;
    }
    public int getProductId(String productName){
        db = this.getReadableDatabase();
        int productId = 0;
        String query = "SELECT "+id+" FROM "+TableProducts+" WHERE "+ProductName+"='"+productName+"' LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            productId = cursor.getInt(0);
        }
        return productId;
    }
    public List<Integer> getProductsId(String productName){
        db = this.getReadableDatabase();
        List<Integer> Ids = new ArrayList<>();
        String query = "SELECT "+id+" FROM "+TableProducts+" WHERE "+ProductName+"='"+productName+"'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                Ids.add(cursor.getInt(0));
            }while(cursor.moveToNext());
        }
        return Ids;
    }
    public int getProductsIdFromCode(String code){
        db = this.getReadableDatabase();
        int productId = 0;
        String query = "SELECT "+id+" FROM "+TableProducts+" WHERE "+Code+"='"+code+"' LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            productId = cursor.getInt(0);
        }
        return productId;
    }
    public boolean checkProductFromCode(String code){
        db = this.getReadableDatabase();
        boolean found = false;
        String query = "SELECT COUNT(id) FROM "+TableProducts+" WHERE "+Code+"='"+code+"' LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            if(cursor.getInt(0)>0){
                found = true;
            }
        }
        return found;
    }
    public int getProductId(String productName, String description){
        db = this.getReadableDatabase();
        int productId = 0;
        String query = "SELECT "+id+" FROM "+TableProducts+" WHERE "+ProductName+"='"+productName+"' AND "+Description+"='"+description+"' LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            productId = cursor.getInt(0);
        }
        return productId;
    }
    /*
    Profits
     */
    public boolean checkTableProfits(){
        db = this.getReadableDatabase();
        boolean found = false;
        String query = "SELECT COUNT(id) FROM "+TableProfits;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            if(cursor.getInt(0)>0){
                found = true;
            }
        }
        return found;
    }
    public void addTableProfits(int profit, int estimatedProfit, boolean update){
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(ProfitMade, profit);
        values.put(EstimatedProfit, estimatedProfit);

        if(update){
            db.update(TableProfits, values, id+"=1", null);
        }else{
            db.insert(TableProfits, null, values);
        }
    }
    public int[] getProfits(){
        db = this.getReadableDatabase();
        int[] profits = new int[]{0,0,0};
        String query = "SELECT "+ProfitMade+", "+EstimatedProfit+" FROM "+TableProfits;
        Cursor cursor =  db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            profits[0] = cursor.getInt(0);
            profits[1] = cursor.getInt(1);
        }
        return profits;
    }
    /*
    Removed products
     */
    public int getRecycleBinCount(){
        db = this.getReadableDatabase();
        int found = 0;
        String query = "SELECT COUNT(id) AS found  FROM "+TableRemovedProducts;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            found = cursor.getInt(0);
        }
        return found;
    }
    public void addRemovedProducts(String productName, String description, int amount, String category, int purchasePrice, int sellingPrice,
                                   String code, String reason, int price, byte[] productImage, String date){
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(ProductName, productName);
        values.put(Description, description);
        values.put(Amount, amount);
        values.put(Category, category);
        values.put(PurchasePrice, purchasePrice);
        values.put(SellingPrice, sellingPrice);
        values.put(Code, code);
        values.put(Reason, reason);
        values.put(Price, price);
        values.put(ProductImage, productImage);
        values.put(DDate, date);

        db.insert(TableRemovedProducts, null, values);
    }
    public boolean checkRemovedProductFromCode(String code){
        db = this.getReadableDatabase();
        boolean found = false;
        String query = "SELECT COUNT(id) AS found FROM "+TableRemovedProducts+" WHERE "+Code+"='"+code+"'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            if(cursor.getInt(0)>0){
                found = true;
            }
        }
        return found;
    }
    public List<DbHelper> getRemovedProductList(){
        db = this.getReadableDatabase();
        List<DbHelper> dbHelperList = new ArrayList<>();
        String query = "SELECT "+id+", "+ProductName+", "+Category+", "+Amount+", "+DDate+" FROM "+TableRemovedProducts+" ORDER BY id DESC";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                DbHelper dbHelper = new DbHelper();
                dbHelper.setId(cursor.getInt(0));
                dbHelper.setProductsName(cursor.getString(1));
                dbHelper.setCategory(cursor.getString(2));
                dbHelper.setAmount(cursor.getInt(3));
                dbHelper.setDDate(cursor.getString(4));

                dbHelperList.add(dbHelper);
            }while (cursor.moveToNext());
        }
        return dbHelperList;
    }
    public List<ProductList> getSpecificProductFromRecycleBin(int Id){
        db = this.getReadableDatabase();
        List<ProductList> productListList = new ArrayList<>();
        String query = "SELECT "+ProductName+", "+Description+", "+Amount+", "+Category+", "+PurchasePrice+", "+SellingPrice+", "+
                Code+", "+DDate+" FROM "+TableRemovedProducts+" WHERE "+id+"="+Id;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            ProductList productList = new ProductList();

            productList.setProductName(cursor.getString(0));
            productList.setDescription(cursor.getString(1));
            productList.setAmount(cursor.getInt(2));
            productList.setCategory(cursor.getString(3));
            productList.setPurchasePrice(cursor.getInt(4));
            productList.setSellingPrice(cursor.getInt(5));
            productList.setCode(cursor.getString(6));
            productList.setDDate(cursor.getString(7));

            productListList.add(productList);
        }
        return productListList;
    }
    public byte[] getProductImageRecycleBin(int Id){
        db = this.getReadableDatabase();
        byte[] productImage = new byte[0];
        String query = "SELECT "+ProductImage+" FROM "+TableRemovedProducts+" WHERE "+id+"="+Id;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            productImage = cursor.getBlob(0);
        }
        return productImage;
    }
    public void deleteFromRecycleBin(int Id){
        db = this.getReadableDatabase();
        db.delete(TableRemovedProducts, id+"="+Id, null);
    }
    /*
    Expiry
     */
    public void AddExpiry(int productId, String date, boolean update){
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(ProductId, productId);
        values.put(ExpiryDate, date);
        if(!update){
            db.insert(TableExpiryDate, null, values);
        }else{
            db.update(TableExpiryDate, values, ProductId+"="+productId, null);
        }
    }
    public boolean searchExpiryExists(int productId){
        db = this.getReadableDatabase();
        boolean found = false;
        String query = "SELECT COUNT(id) AS found FROM "+TableExpiryDate+" WHERE "+ProductId+"="+productId;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            if(cursor.getInt(0)>0){
                found = true;
            }
        }
        return found;
    }
    public String getExpiryDate(int productId){
        db = this.getReadableDatabase();
        String expiryDate = "";
        String query = "SELECT "+ExpiryDate+" FROM "+TableExpiryDate+" WHERE "+ProductId+"="+productId;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            expiryDate = cursor.getString(0);
        }
        return expiryDate;
    }
    /*
    History products
     */
    public int CheckTableProductHistory(){
        db = this.getReadableDatabase();
        int n = 0;
        String query = "SELECT COUNT() FROM "+TableHistoryProducts;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            n = cursor.getInt(0);
        }
        return n;
    }
    public void saveToProductHistory(String productName, String description, int amount, String category, int purchasePrice,
                                     int sellingPrice, String barCode, String purchaseOrSale, int priceSold, byte[] productImage,
                                     String date){
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(ProductName, productName);
        values.put(Description, description);
        values.put(Amount, amount);
        values.put(Category, category);
        values.put(PurchasePrice, purchasePrice);
        values.put(SellingPrice, sellingPrice);
        values.put(Code, barCode);
        values.put(PurchaseOrSold, purchaseOrSale);
        values.put(PriceSold, priceSold);
        values.put(ProductImage, productImage);
        values.put(DDate, date);

        db.insert(TableHistoryProducts, null, values);
    }
    public int getProductHistoryId(String productName, String code, String date, String purchaseOrSold){
        db = this.getReadableDatabase();
        int Id = 0;
        String query = "SELECT "+id+" FROM "+TableHistoryProducts+" WHERE "+ProductName+"='"+productName+"' AND "+Code+"='"+code+"' AND "+PurchaseOrSold+"='"+purchaseOrSold+"' AND "+DDate+"='"+date+"'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            Id = cursor.getInt(0);
        }
        return Id;
    }
    public int getProductHistoryAmount(int Id){
        db = this.getReadableDatabase();
        int amount = 0;
        String query = "SELECT "+Amount+" FROM "+TableHistoryProducts+" WHERE "+id+"="+Id;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            amount = cursor.getInt(0);
        }
        return amount;
    }
    public void updateProductHistory(String productName, String description, int amount, String category, int purchasePrice,
                                     int sellingPrice, String barCode, String purchaseOrSale, int priceSold, byte[] productImage, int Id){
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(ProductName, productName);
        values.put(Description, description);
        values.put(Amount, amount);
        values.put(Category, category);
        values.put(PurchasePrice, purchasePrice);
        values.put(SellingPrice, sellingPrice);
        values.put(Code, barCode);
        values.put(PurchaseOrSold, purchaseOrSale);
        values.put(PriceSold, priceSold);
        values.put(ProductImage, productImage);

        db.update(TableHistoryProducts, values, id+"="+Id, null);
    }
    public List<DbHelper> getSpecificProductFromHistory(int Id){
        db = this.getReadableDatabase();
        List<DbHelper> dbHelperList = new ArrayList<>();
        String query = "SELECT "+id+", "+ProductName+", "+Description+", "+Amount+", "+Category+", "+PurchasePrice+", "+SellingPrice+", "+
                Code+", "+PurchaseOrSold+", "+PriceSold+", "+DDate+" FROM "+TableHistoryProducts+" WHERE id="+Id;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            DbHelper dbHelper = new DbHelper();

            dbHelper.setId(cursor.getInt(0));
            dbHelper.setProductsName(cursor.getString(1));
            dbHelper.setDescription(cursor.getString(2));
            dbHelper.setAmount(cursor.getInt(3));
            dbHelper.setCategory(cursor.getString(4));
            dbHelper.setPurchasePrice(cursor.getInt(5));
            dbHelper.setSellingPrice(cursor.getInt(6));
            dbHelper.setCode(cursor.getString(7));
            dbHelper.setPurchaseOrSale(cursor.getString(8));
            dbHelper.setTotalPrice(cursor.getInt(9));
            dbHelper.setDDate(cursor.getString(10));

            dbHelperList.add(dbHelper);
        }
        return dbHelperList;
    }
    public List<DbHelper> getProfitsData(boolean sold){
        db = this.getReadableDatabase();
        List<DbHelper> dbHelperList = new ArrayList<>();
        String query;
        if(sold){
            query = "SELECT "+Amount+", "+PurchasePrice+", "+SellingPrice+" FROM "+TableHistoryProducts+" WHERE "+PurchaseOrSold+"='sold'";
        }else {
            query = "SELECT "+Amount+", "+PurchasePrice+", "+SellingPrice+" FROM "+TableHistoryProducts+" WHERE "+PurchaseOrSold+"='purchased'";
        }
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                DbHelper dbHelper = new DbHelper();

                dbHelper.setAmount(cursor.getInt(0));
                dbHelper.setPurchasePrice(cursor.getInt(1));
                dbHelper.setSellingPrice(cursor.getInt(2));

                dbHelperList.add(dbHelper);
            }while(cursor.moveToNext());
        }
        return dbHelperList;
    }
    public DbHelper getSpecificProductFromHistory(String date){
        db = this.getReadableDatabase();
        String query = "SELECT "+id+", "+ProductName+", "+Description+", "+Amount+", "+Category+", "+PurchasePrice+", "+SellingPrice+", "+
                Code+", "+PurchaseOrSold+", "+PriceSold+", "+DDate+" FROM "+TableHistoryProducts+" WHERE "+DDate+"='"+date+"'";
        Cursor cursor = db.rawQuery(query, null);
        DbHelper dbHelper = new DbHelper();
        if (cursor.moveToFirst()){
            dbHelper.setId(cursor.getInt(0));
            dbHelper.setProductsName(cursor.getString(1));
            dbHelper.setDescription(cursor.getString(2));
            dbHelper.setAmount(cursor.getInt(3));
            dbHelper.setCategory(cursor.getString(4));
            dbHelper.setPurchasePrice(cursor.getInt(5));
            dbHelper.setSellingPrice(cursor.getInt(6));
            dbHelper.setCode(cursor.getString(7));
            dbHelper.setPurchaseOrSale(cursor.getString(8));
            dbHelper.setTotalPrice(cursor.getInt(9));
            dbHelper.setDDate(cursor.getString(10));
        }
        return dbHelper;
    }
    public List<DbHelper> getProductHistory(@NonNull String date){
        db = this.getReadableDatabase();
        List<DbHelper> dbHelperList = new ArrayList<>();
        String newDate = "";
        int n = 0;
        String query = "";
        switch (date){
            case "All":
                query = "SELECT "+id+", "+ProductName+", "+Description+", "+Amount+", "+PurchaseOrSold+", "+DDate+" FROM "+TableHistoryProducts+" ORDER BY id DESC";
                break;
            case "Home":
                query = "SELECT "+id+", "+ProductName+", "+Description+", "+Amount+", "+PurchaseOrSold+", "+DDate+" FROM "+TableHistoryProducts+" ORDER BY id DESC LIMIT 7";
                break;
            default:
                query = "SELECT "+id+", "+ProductName+", "+Description+", "+Amount+", "+PurchaseOrSold+", "+DDate+" FROM "+TableHistoryProducts+" WHERE "+DDate+"='"+date+"' ORDER BY id DESC";
                break;
        }
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                DbHelper dbHelper = new DbHelper();

                dbHelper.setId(cursor.getInt(0));
                dbHelper.setProductsName(cursor.getString(1));
                dbHelper.setDescription(cursor.getString(2));
                dbHelper.setAmount(cursor.getInt(3));
                dbHelper.setPurchaseOrSale(cursor.getString(4));
                dbHelper.setDDate(cursor.getString(5));

                if(n==0){
                    dbHelper.setSetDate(true);
                    newDate = cursor.getString(5);
                    n++;
                }else{
                    if(!cursor.getString(5).equals(newDate)){
                        dbHelper.setSetDate(true);
                        newDate = cursor.getString(5);
                    }
                }

                dbHelperList.add(dbHelper);
            }while (cursor.moveToNext());
        }
        return dbHelperList;
    }
    public byte[] getProductHistoryImage(int Id){
        db = this.getReadableDatabase();
        byte[] image = new byte[0];
        String query = "SELECT "+ProductImage+" FROM "+TableHistoryProducts+" where id="+Id;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            image = cursor.getBlob(0);
        }
        return image;
    }
    public List<DataEntry> getDateFromHistoryProducts(){
        db = this.getReadableDatabase();
        List<DataEntry> data = new ArrayList<>();
        List<String> dates = new ArrayList<>();
        String query = "SELECT "+DDate+" FROM "+TableHistoryProducts+" ORDER BY "+DDate+" ASC";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do {
                dates.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        List<String> date = dates.stream().distinct().sorted().collect(Collectors.toList());
        for(String dt: date){
            data.add(new ValueDataEntry(getDateName(dt), getDailySalesGraph(dt)));
        }
        return data;
    }
    @SuppressLint("SimpleDateFormat")
    private String getDateName(String date){
        String dayName = date;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMMM/yyyy hh:mm:s");
        try {
            @SuppressLint("SimpleDateFormat") Date day=new SimpleDateFormat("yyyy-MM-dd").parse(date);
            @SuppressLint("SimpleDateFormat") Format f  = new SimpleDateFormat("EEEE");
            dayName = f.format(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dayName;
    }
    public int getDailySalesGraph(String date){
        db = this.getReadableDatabase();
        int price = 0;
        String query = "SELECT "+PriceSold+" FROM "+TableHistoryProducts+" WHERE "+DDate+"='"+date+"'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                price+=cursor.getInt(0);
            }while (cursor.moveToNext());
        }
        return price;
    }
    /*
    Payment method
     */
    public boolean checkPaymentMethodId(int historyId){
        db = this.getReadableDatabase();
        boolean found = false;
        String query = "SELECT COUNT(id) AS found FROM "+TablePaymentMethod+" WHERE "+HistoryId+"="+historyId;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            if(cursor.getInt(0)>0){
                found = true;
            }
        }
        return found;
    }
    public int getPaymentMethodId(String paymentMethod){
        db = this.getReadableDatabase();
        int Id = 0;
        String query = "SELECT "+id+" FROM "+TablePaymentMethod+" WHERE "+PaymentMethod+"='"+paymentMethod+"'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            Id = cursor.getInt(0);
        }
        return Id;
    }
    public void AddPaymentMethod(int historyId, String paymentMethod){
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(HistoryId, historyId);
        values.put(PaymentMethod, paymentMethod);
        db.insert(TablePaymentMethod, null, values);
    }
    public void AddPaymentMethodLink(int historyId, int paymentMethodId){
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(HistoryId, historyId);
        values.put(PaymentMethodId, paymentMethodId);

        db.insert(TablePaymentMethodLink, null, values);
    }
    public String getPaymentMethod(int historyId){
        db = this.getReadableDatabase();
        String paymentMethod = "Cash";
        String query = "SELECT "+PaymentMethod+" FROM "+TablePaymentMethod+" WHERE "+HistoryId+"="+historyId;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            paymentMethod = cursor.getString(0);
        }
        return paymentMethod;
    }
    /*
    Employees
     */
    public int checkEmployees(){
        db = this.getReadableDatabase();
        int a = 0;
        String query = "SELECT COUNT() FROM "+TableEmployee;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            a = cursor.getInt(0);
        }
        return a;
    }
    public boolean CheckEmployee(String name, String email){
        db = this.getReadableDatabase();
        boolean found = false;
        String query = "SELECT COUNT() FROM "+TableEmployee+" WHERE "+Name+"='"+name+"' AND "+Email+"='"+email+"'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            if(cursor.getInt(0)>0){
                found = true;
            }
        }
        return found;
    }
    public void AddEmployee(String name, String email, String phone, String bank, String date){
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(Name, name);
        values.put(Email, email);
        values.put(Phone, phone);
        values.put(Bank, bank);
        values.put(Paid, "NotPaid");
        values.put(DDate, date);

        db.insert(TableEmployee, null, values);
    }
    public void savePaymentHistory(String name, String email, String phone, String bank, String startDate, String endDate, int amount, String date){
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(Name, name);
        values.put(Email, email);
        values.put(Phone, phone);
        values.put(Bank, bank);
        values.put(StartDate, startDate);
        values.put(EndDate, endDate);
        values.put(Amount, amount);
        values.put(DDate, date);

        db.insert(TablePaymentHistory, null, values);
    }
    public void updatePayment(int Id){
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(Paid, "Paid");
        db.update(TableEmployee, values, "id="+Id, null);
    }
    public void UpdatePaid(){
        db = this.getReadableDatabase();
        String query = "UPDATE "+TableEmployee+" SET "+Paid+"='NotPaid'";
        db.execSQL(query);
    }
    public List<PojoGetEmployee> getEmployeeData(int Id){
        db = this.getReadableDatabase();
        List<PojoGetEmployee> getEmployee = new ArrayList<>();
        String query = "SELECT "+Name+", "+Email+", "+Phone+", "+Bank+" FROM "+TableEmployee+" WHERE id="+Id;
        Cursor cursor= db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            getEmployee.add(new PojoGetEmployee(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
        }
        return getEmployee;
    }
    public boolean checkEmployeeName(String name){
        db = this.getReadableDatabase();
        boolean found = false;
        String query = "SELECT COUNT(id) AS found FROM "+TableEmployee+" WHERE "+Name+"='"+name+"'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            if(cursor.getInt(0)>0){
                found = true;
            }
        }
        return found;
    }
    public List<DbHelper> getPaymentList(@NonNull String condition){
        db = this.getReadableDatabase();
        List<DbHelper> dbHelperList = new ArrayList<>();
        String query = null;
        if(condition.equals("Paid")){
            query = "SELECT "+id+", "+Name+", "+Phone+", "+Paid+" FROM "+TableEmployee+" WHERE "+Paid+"='Paid'";
        }else if(condition.equals("NotPaid")){
            query = "SELECT "+id+", "+Name+", "+Phone+", "+Paid+" FROM "+TableEmployee+" WHERE "+Paid+"='NotPaid'";
        }else{
            query = "SELECT "+id+", "+Name+", "+Phone+", "+Paid+" FROM "+TableEmployee;
        }
        db.rawQuery(query, null);
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                DbHelper dbHelper = new DbHelper();

                dbHelper.setId(cursor.getInt(0));
                dbHelper.setName(cursor.getString(1));
                dbHelper.setPhone(cursor.getString(2));
                dbHelper.setPaid(cursor.getString(3));

                dbHelperList.add(dbHelper);
            }while (cursor.moveToNext());
        }
        return dbHelperList;
    }
    public List<DbHelper> getEmployeePaymentList(String name){
        db = this.getReadableDatabase();
        List<DbHelper> dbHelperList = new ArrayList<>();
        String query = "SELECT "+id+", "+StartDate+", "+EndDate+", "+Amount+", "+DDate+" FROM "+TablePaymentHistory+" WHERE "+Name+"='"+name+"'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                DbHelper dbHelper = new DbHelper();
                dbHelper.setId(cursor.getInt(0));
                dbHelper.setStartDate(cursor.getString(1));
                dbHelper.setEndDate(cursor.getString(2));
                dbHelper.setAmount(cursor.getInt(3));
                dbHelper.setDDate(cursor.getString(4));

                dbHelperList.add(dbHelper);
            }while (cursor.moveToNext());
        }
        return dbHelperList;
    }

    public List<DbHelper> getEmployeeList(){
        db = this.getReadableDatabase();
        List<DbHelper> dbHelperList = new ArrayList<>();
        String query = "SELECT "+id+", "+Name+", "+Email+", "+Phone+" FROM "+TableEmployee;
        db.rawQuery(query, null);
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                DbHelper dbHelper = new DbHelper();

                dbHelper.setId(cursor.getInt(0));
                dbHelper.setName(cursor.getString(1));
                dbHelper.setEmail(cursor.getString(2));
                dbHelper.setPhone(cursor.getString(3));

                dbHelperList.add(dbHelper);
            }while (cursor.moveToNext());
        }
        return dbHelperList;
    }
    public List<String> getEmployeesPaymentHistoryNames(){
        db = this.getReadableDatabase();
        List<String> name = new ArrayList<>();
        String query = "SELECT "+Name+" FROM "+TablePaymentHistory;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                name.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        return name;
    }
    public PojoPaymentHistory getEmployeePaymentHistory(String name){
        db =this.getReadableDatabase();
        PojoPaymentHistory paymentHistory = new PojoPaymentHistory();
        String query = "SELECT "+Name+", "+Email+", "+Phone+", "+Bank+", "+StartDate+", "+EndDate+", "+DDate+" FROM "+TablePaymentHistory+" WHERE "+Name+"='"+name+"'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            paymentHistory.setName(cursor.getString(0));
            paymentHistory.setFound(checkEmployeeName(cursor.getString(0)));
            paymentHistory.setNumber(cursor.getString(2));
        }
        return paymentHistory;
    }
    /*
    Temp tables
     */
    public boolean checkTempProductsTable(){
        db = this.getReadableDatabase();
        boolean found = false;
        Cursor cursor = null;
        try{
            cursor = db.query(TempProductTable, null, null, null, null, null, null);

            String query = "SELECT COUNT(id) AS more FROM "+TempProductTable;
            Cursor cursor1 = db.rawQuery(query, null);
            if(cursor1.moveToFirst()){
                if(cursor1.getInt(0)>0){
                    found = true;
                }
            }
        }catch (Exception e){
            //
        }
        return found;
    }
    public void addProductTempTable(String category, String productName, String description, int amount, int productId, int price){
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(Category, category);
        values.put(ProductName, productName);
        values.put(Description, description);
        values.put(Amount, amount);
        values.put(ProductId, productId);
        values.put(Price, price);
        db.insert(TempProductTable, null, values);
    }
    public int getTempProductCount(){
        db = this.getReadableDatabase();
        int count = 0;
        String query = "SELECT COUNT(id) FROM "+TempProductTable;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            count = cursor.getInt(0);
        }
        return count;
    }
    public void deleteTempProduct(int productId){
        db = this.getReadableDatabase();
        db.delete(TempProductTable, "id="+productId, null);
    }
    public int getTotalPrice(){
        db = this.getReadableDatabase();
        int total = 0;
        String query = "SELECT "+Price+" FROM "+TempProductTable;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                total += cursor.getInt(0);
            }while(cursor.moveToNext());
        }
        return total;
    }
    public int getTotalAmount(){
        db = this.getReadableDatabase();
        int total = 0;
        String query = "SELECT "+Amount+" FROM "+TempProductTable;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                total += cursor.getInt(0);
            }while(cursor.moveToNext());
        }
        return total;
    }
    public void dropTableTempProduct(){
        db = this.getReadableDatabase();
        String query = "DROP TABLE "+TempProductTable;
        db.execSQL(query);
        db.close();
    }
    public List<DbHelper> getShoppingList(){
        db = this.getReadableDatabase();
        String query = "SELECT "+id+", "+Category+", "+ProductName+", "+Amount+", "+ProductId+", "+Price+" FROM "+TempProductTable;
        List<DbHelper> dbHelperList = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                DbHelper helper = new DbHelper();

                helper.setId(cursor.getInt(0));
                helper.setCategory(cursor.getString(1));
                helper.setProductsName(cursor.getString(2));
                helper.setAmount(cursor.getInt(3));
                helper.setProductId(cursor.getInt(4));
                helper.setTotalPrice(cursor.getInt(5));

                dbHelperList.add(helper);
            }while (cursor.moveToNext());
        }
        return dbHelperList;
    }
    public int getAmountFromTempTable(String productName, String description){
        db = this.getReadableDatabase();
        int amt = 0;
        String query = "SELECT "+Amount+" FROM "+TempProductTable+" WHERE "+ProductName+"='"+productName+"' AND "+Description+"='"+description+"'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                amt+=cursor.getInt(0);
            }while (cursor.moveToNext());
        }
        return amt;
    }
    /*
    Other expenses
     */
    public List<DbHelper> getOtherExpensesList(){
        db = this.getReadableDatabase();
        List<DbHelper> dbHelperList = new ArrayList<>();
        String query = "SELECT "+id+", "+Type+", "+Description+", "+Amount+", "+ImageCode+", "+DDate+" FROM "+TableOtherExpense;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                DbHelper dbHelper = new DbHelper();

                dbHelper.setId(cursor.getInt(0));
                dbHelper.setType(cursor.getString(1));
                dbHelper.setDescription(cursor.getString(2));
                dbHelper.setAmount(cursor.getInt(3));
                dbHelper.setImageCode(cursor.getInt(4));
                dbHelper.setDDate(cursor.getString(5));

                dbHelperList.add(dbHelper);
            }while(cursor.moveToNext());
        }
        return dbHelperList;
    }
    public void AddOtherExpenses(String type, String description, int amount, int imageCode, @NonNull SimpleDateFormat date){
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(Type, type);
        values.put(Description, description);
        values.put(Amount, amount);
        values.put(ImageCode, imageCode);
        values.put(DDate, date.format(new Date()));

        db.insert(TableOtherExpense, null, values);
    }
    public String getOtherExpensesDescription(int Id){
        db = this.getReadableDatabase();
        String description="";
        String query = "SELECT "+Description+" FROM "+TableOtherExpense+" WHERE id="+Id;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            description = cursor.getString(0);
        }
        return description;
    }
    public int getOtherExpenseTotalAmount(){
        db = this.getReadableDatabase();
        int total = 0;
        String query = "SELECT "+Amount+" FROM "+TableOtherExpense;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                total+=cursor.getInt(0);
            }while (cursor.moveToNext());
        }
        return total;
    }
    /*
    Total sales
     */
    public void UpdateTotalPurchase(int total, boolean update){
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(id, 1);
        values.put(TotalSales, total);
        if(update){
            db.update(TableTotalSales, values, "id="+1, null);
        }else{
            db.insert(TableTotalSales, null, values);
        }
    }
    public int calculateTotalSales(){
        db = this.getReadableDatabase();
        int total = 0;
        String query = "SELECT "+PriceSold+" FROM "+TableHistoryProducts;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                total += cursor.getInt(0);
            }while(cursor.moveToNext());
        }
        return total;
    }
    public int getTotalSales(){
        db = this.getReadableDatabase();
        int total = 0;
        String query = "SELECT "+TotalSales+" FROM "+TableTotalSales;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            total = cursor.getInt(0);
        }
        return total;
    }
    public int getTotalSalesRowCount(){
        db = this.getReadableDatabase();
        int count = 0;
        String query = "SELECT COUNT(id) FROM "+TableTotalSales;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            count = cursor.getInt(0);
        }
        return count;
    }
    /*
    Purchases
     */
    public void updateTotalPurchases(int amount){
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(id, 1);
        values.put(TotalPurchases, amount);
        if(checkPurchases()){
            db.update(TableTotalPurchases, values, "id=1", null);
        }else{
            db.insert(TableTotalPurchases, null, values);
        }
    }
    public int getTotalPurchases(){
        db = this.getReadableDatabase();
        int total = 0;
        String query = "SELECT "+TotalPurchases+" FROM "+TableTotalPurchases;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            total = cursor.getInt(0);
        }
        return total;
    }
    public boolean checkPurchases(){
        db = this.getReadableDatabase();
        boolean found = false;
        String query = "SELECT COUNT(id) FROM "+TableTotalPurchases;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            if(cursor.getInt(0)>0){
                found = true;
            }
        }
        return found;
    }
    public List<CalculateData> getPurchasesData(){
        db = this.getReadableDatabase();
        List<CalculateData> dataList = new ArrayList<>();
        String query = "SELECT "+Amount+", "+PurchasePrice+" FROM "+TableHistoryProducts+" WHERE "+PurchaseOrSold+"='purchased'";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do {
                dataList.add(new CalculateData(cursor.getInt(0), cursor.getInt(1)));
            }while(cursor.moveToNext());
        }
        return dataList;
    }
    public int getTodayTotalSales(String date){
        db = this.getReadableDatabase();
        int total = 0;
        String query  = "SELECT SUM("+PriceSold+") FROM "+TableHistoryProducts+" WHERE "+DDate+"='"+date+"'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            total = cursor.getInt(0);
        }
        return total;
    }
    /*
    Table Store
     */
    public boolean checkTableStore(){
        db = this.getReadableDatabase();
        boolean empty = false;
        String query = "SELECT COUNT(id) FROM "+TableStores;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            if(cursor.getInt(0)>0){
                empty = true;
            }
        }
        return empty;
    }
    public boolean checkTableUser(){
        db = this.getReadableDatabase();
        boolean empty = false;
        String query = "SELECT COUNT(id) FROM "+TableUser;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            if(cursor.getInt(0)>0){
                empty = true;
            }
        }
        return empty;
    }
    public void AddStore(String storeName, String accountType, byte[] image){
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(StoreName, storeName);
        values.put(AccountType, accountType);
        values.put(Image, image);
        if(checkTableStore()){
            db.update(TableStores, values, "id=1", null);
        }else{
            values.put(id, 1);
            db.insert(TableStores, null, values);
        }
    }
    public void AddUser(String userName, String password){
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(User, userName);
        values.put(Password, password);
        if(checkTableUser()){
            db.update(TableUser, values, "id=1", null);
        }else{
            values.put(id, 1);
            db.insert(TableUser, null, values);
        }
    }
    public String getAccountType(){
        db = this.getReadableDatabase();
        String accountType = "";
        String query = "SELECT "+AccountType+" FROM "+TableStores;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            accountType = cursor.getString(0);
        }
        return accountType;
    }
    public String getUser(){
        db = this.getReadableDatabase();
        String user = "";
        String query = "SELECT "+User+" FROM "+TableUser;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            user = cursor.getString(0);
        }
        return user;
    }
    public String getAccountPassword(){
        db = this.getReadableDatabase();
        String query = "SELECT "+Password+" FROM "+TableUser;
        String password = "";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            password = cursor.getString(0);
        }
        return password;
    }
    public String getStoreName(){
        db = this.getReadableDatabase();
        String storeName = "";
        String query = "SELECT "+StoreName+" FROM "+TableStores;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            storeName = cursor.getString(0);
        }
        return storeName;
    }
    public byte[] getStoreImage(){
        db = this.getReadableDatabase();
        byte[] image = new byte[0];
        String query = "SELECT "+Image+" FROM "+TableStores;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            image = cursor.getBlob(0);
        }
        return image;
    }
    /*
    Devices
     */
    public boolean CheckDevice(String userName){
        db = this.getReadableDatabase();
        boolean found = false;
        String query = "SELECT COUNT(id) AS found FROM "+TableDevices+" WHERE "+DeviceUserName+"='"+userName+"'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            if(cursor.getInt(0)>0){
                found = true;
            }
        }return found;
    }
    public void addDevices(String deviceUserName, String password, String customOrEmployee){
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(DeviceUserName, deviceUserName);
        values.put(DevicePassword, password);
        values.put(CustomOrEmployee, customOrEmployee);

        db.insert(TableDevices, null, values);
    }
    public List<DbHelper> getDevicesList(){
        db = this.getReadableDatabase();
        List<DbHelper> dbHelperList = new ArrayList<>();
        String query = "SELECT id, "+DeviceUserName+" FROM "+TableDevices;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                DbHelper dbHelper = new DbHelper();
                dbHelper.setId(cursor.getInt(0));
                dbHelper.setName(cursor.getString(1));
                dbHelperList.add(dbHelper);
            }while (cursor.moveToNext());
        }
        return dbHelperList;
    }
    /*
    Security question
     */
    private boolean checkSecurityQuestion(){
        db = this.getReadableDatabase();
        boolean found = false;
        String query = "SELECT COUNT(id) FROM "+SecurityQuestionTable;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            if(cursor.getInt(0)>0){
                found = true;
            }
        }
        return found;
    }
    public void saveSecurityQuestion(String answer){
        db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(SecurityQuestion, answer);
        if(checkSecurityQuestion()){
            db.update(SecurityQuestionTable, values, id+"="+1, null);
        }else{
            values.put(id, 1);
            db.insert(SecurityQuestionTable, null, values);
        }
    }
    /*
    Create tables
     */
    private void CreateTableStore(){
        db = this.getReadableDatabase();
        String query = "CREATE TABLE IF NOT EXISTS "+TableStores+"( " +id+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                StoreName+" VARCHAR(50) NOT NULL, " +
                AccountType+" TEXT  NOT NULL, "+
                Image+" BLOB NOT NULL );";
        db.execSQL(query);
    }
    private void CreateTableProducts(){
        db = this.getReadableDatabase();
        String query = "CREATE TABLE IF NOT EXISTS "+TableProducts+"( "+id+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                ProductName+" VARCHAR(100) NOT NULL, "+
                Description+" VARCHAR(100) NOT NULL, "+
                Amount+" INTEGER NOT NULL, "+
                CategoryID+" INTEGER NOT NULL, "+
                PriceID+" INTEGER NOT NULL, "+
                Code+" VARCHAR(100) NOT NULL, "+
                DDate+" DATE NOT NULL, " +
                ProductImage+" BLOB NOT NULL, "+
                "CONSTRAINT Price_column FOREIGN KEY ("+PriceID+") REFERENCES "+TablePrice+"("+id+"), "+
                "CONSTRAINT Category_column FOREIGN KEY ("+CategoryID+") REFERENCES "+TableCategory+"("+id+")"+");";
        db.execSQL(query);
    }
    private void CreateTableCategory(){
        db = this.getReadableDatabase();
        String query = "CREATE TABLE IF NOT EXISTS "+TableCategory+"( "+id+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                Category+" TEXT NOT NULL);";
        db.execSQL(query);
    }
    private void CreateTablePrices(){
        db = this.getReadableDatabase();
        String query = "CREATE TABLE IF NOT EXISTS "+TablePrice+"( "+id+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                PurchasePrice+" INTEGER NOT NULL, "+
                SellingPrice+" INTEGER NOT NULL);";
        db.execSQL(query);
    }
    private void CreateTableEmployee(){
        db = this.getReadableDatabase();
        String query = "CREATE TABLE IF NOT EXISTS "+TableEmployee+"( "+id+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                Name+" VARCHAR(100) NOT NULL, "+
                Email+" VARCHAR(100) NOT NULL, "+
                Phone+" VARCHAR(30) NOT NULL, "+
                Bank+" VARCHAR(50) NOT NULL," +
                Paid+" VARCHAR(40) NOT NULL, "+
                DDate+" DATE NOT NULL);";
        db.execSQL(query);
    }
    private void CreateTableHistory(){
        db = this.getReadableDatabase();
        String query = "CREATE TABLE IF NOT EXISTS "+TableHistoryProducts+"( "+id+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                ProductName+" VARCHAR(100) NOT NULL, "+
                Description+" VARCHAR(100) NOT NULL, "+
                Amount+" INTEGER NOT NULL, "+
                Category+" INTEGER NOT NULL, "+
                PurchasePrice+" INTEGER NOT NULL, "+
                SellingPrice+" INTEGER NOT NULL, "+
                Code+" VARCHAR(100) NOT NULL, "+
                PurchaseOrSold+" VARCHAR(100) NOT NULL, "+
                PriceSold+" INTEGER NOT NULL, "+
                ProductImage+" BLOB NOT NULL, "+
                DDate+" DATE NOT NULL);";
        db.execSQL(query);
    }
    public void createTempTable(){
        db = this.getReadableDatabase();
        String query = "CREATE TABLE IF NOT EXISTS "+TempProductTable+"( "+id+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                Category+" TEXT NOT NULL, "+
                ProductName+" TEXT NOT NULL, "+
                Description+" TEXT NOT NULL, "+
                Amount+" INTEGER NOT NULL, "+
                ProductId+" INTEGER NOT NULL, " +
                Price+" INTEGER NOT NULL)";
        db.execSQL(query);
    }
    private void createTableOtherExpense(){
        db = this.getReadableDatabase();
        String query = "CREATE TABLE IF NOT EXISTS "+TableOtherExpense+"( "+id+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                Type+" TEXT NOT NULL, "+
                Description+" TEXT NOT NULL, "+
                Amount+" INTEGER NOT NULL, "+
                ImageCode+" INTEGER NOT NULL, "+
                DDate+" DATE NOT NULL);";
        db.execSQL(query);
    }
    private void createTableTotalSales(){
        db = this.getReadableDatabase();
        String query = "CREATE TABLE IF NOT EXISTS "+TableTotalSales+"( "+id+" INTEGER NOT NULL, "+
                TotalSales+" INTEGER NOT NULL);";
        db.execSQL(query);
    }
    private void CreateTableTotalPurchases(){
        db = this.getReadableDatabase();
        String query = "CREATE TABLE IF NOT EXISTS "+TableTotalPurchases+"( "+id+" INTEGER NOT NULL, "+
                TotalPurchases+" INTEGER NOT NULL);";
        db.execSQL(query);
    }
    private void createTableUser(){
        db = this.getReadableDatabase();
        String query = "CREATE TABLE IF NOT EXISTS "+TableUser+"( "+id+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                User+" TEXT NOT NULL, "+
                Password+" TEXT NOT NULL);";
        db.execSQL(query);
    }
    private void createTablePaymentHistory(){
        db = this.getReadableDatabase();
        String query = "CREATE TABLE IF NOT EXISTS "+TablePaymentHistory+"( "+id+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                Name+" VARCHAR(100) NOT NULL, "+
                Email+" VARCHAR(100) NOT NULL, "+
                Phone+" VARCHAR(30) NOT NULL, "+
                Bank+" VARCHAR(50) NOT NULL," +
                StartDate+" DATE NOT NULL, "+
                EndDate+" DATE NOT NULL, "+
                Amount+" INTEGER NOT NULL, "+
                DDate+" DATE NOT NULL);";
        db.execSQL(query);
    }
    public void createTableDevices(){
        db = this.getReadableDatabase();
        String query = "CREATE TABLE IF NOT EXISTS "+TableDevices+"( "+id+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                DeviceUserName+" TEXT NOT NULL, "+
                DevicePassword+" TEXT NOT NULL, "+
                CustomOrEmployee+" TEXT NOT NULL);";
        db.execSQL(query);
    }
    private void createTableRemovedProducts(){
        db = this.getReadableDatabase();
        String query = "CREATE TABLE IF NOT EXISTS "+TableRemovedProducts+"( "+id+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                ProductName+" VARCHAR(100) NOT NULL, "+
                Description+" VARCHAR(100) NOT NULL, "+
                Amount+" INTEGER NOT NULL, "+
                Category+" INTEGER NOT NULL, "+
                PurchasePrice+" INTEGER NOT NULL, "+
                SellingPrice+" INTEGER NOT NULL, "+
                Code+" VARCHAR(100) NOT NULL, "+
                Reason+" VARCHAR(100) NOT NULL, "+
                Price+" INTEGER NOT NULL, "+
                ProductImage+" BLOB NOT NULL, "+
                DDate+" DATE NOT NULL);";
        db.execSQL(query);
    }
    private void createTableExpiryDate(){
        db = this.getReadableDatabase();
        String query = "CREATE TABLE IF NOT EXISTS "+TableExpiryDate+"( "+id+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                ProductId+" INTEGER NOT NULL, "+
                ExpiryDate+" TEXT NOT NULL, "+
                "CONSTRAINT Product_column FOREIGN KEY("+ProductId+") REFERENCES "+TableProducts+"("+id+"));";
        db.execSQL(query);
    }
    private void createTablePaymentMethod(){
        db = this.getReadableDatabase();
        String query = "CREATE TABLE IF NOT EXISTS "+TablePaymentMethod+"( "+id+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                HistoryId+" INTEGER NOT NULL, "+
                PaymentMethod+" TEXT NOT NULL, "+
                "CONSTRAINT History_column FOREIGN KEY("+HistoryId+") REFERENCES "+TableHistoryProducts+"("+id+"));";
        db.execSQL(query);
    }
    private void createTablePaymentMethodLink(){
        db = this.getReadableDatabase();
        String query = "CREATE TABLE IF NOT EXISTS "+TablePaymentMethodLink+"( "+id+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                HistoryId+" INTEGER NOT NULL, "+
                PaymentMethodId+" INTEGER NOT NULL, "+
                "CONSTRAINT History_column FOREIGN KEY("+HistoryId+") REFERENCES "+TablePaymentMethodLink+"("+id+"), "+
                "CONSTRAINT PaymentMethod_column FOREIGN KEY("+PaymentMethodId+") REFERENCES "+TablePaymentMethodLink+"("+id+"));";
        db.execSQL(query);
    }
    private void createTableProfits(){
        db = this.getReadableDatabase();
        String query = "CREATE TABLE IF NOT EXISTS "+TableProfits+"( "+id+" INTEGER NOT NULL, "+
                ProfitMade+" INTEGER NOT NULL, "+
                EstimatedProfit+" INTEGER NOT NULL);";
        db.execSQL(query);
    }
    private void createSecurityQuestionTable(){
        db = this.getReadableDatabase();
        String query = "CREATE TABLE IF NOT EXISTS "+SecurityQuestionTable+"( "+id+" INTEGER NOT NULL, "+
                SecurityQuestion+" TEXT NOT NULL);";
        db.execSQL(query);
    }
}