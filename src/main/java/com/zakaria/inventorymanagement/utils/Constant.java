package com.zakaria.inventorymanagement.utils;

public interface Constant {
	
	String APP_ROOT = "gestiondestock/v1";
	
	String SUPPLIER_ORDER_ENDPOINT = APP_ROOT + "/supplierorders";
	String CREATE_SUPPLIER_ORDER_ENDPOINT = SUPPLIER_ORDER_ENDPOINT + "/create";
	String FIND_SUPPLIER_ORDER_BY_ID_ENDPOINT = SUPPLIER_ORDER_ENDPOINT + "/{idSupplierOrder}";
	String FIND_SUPPLIER_ORDER_BY_CODE_ENDPOINT = SUPPLIER_ORDER_ENDPOINT + "/filter/{codeSupplierOrder}";
	String FIND_ALL_SUPPLIER_ORDERS_ENDPOINT = SUPPLIER_ORDER_ENDPOINT + "/all";
	String DELETE_SUPPLIER_ORDER_ENDPOINT = SUPPLIER_ORDER_ENDPOINT + "/delete/{idSupplierOrder}";
	
	String COMPANY_ENDPOINT = APP_ROOT + "/companies";
	
	String SUPPLIER_ENDPOINT = APP_ROOT + "/suppliers";
	
	String USER_ENDPOINT = APP_ROOT + "/users";
	
	String SALES_ENDPOINT = APP_ROOT + "/sales";
	
	String AUTHENTICATION_ENDPOINT = APP_ROOT + "/auth";
}
