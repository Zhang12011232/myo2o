$(function() {

	var shopId = getQueryString('shopId');

	var isEdit = shopId ? true : false;

	//var shopInfoUrl = '/myo2o/shop/getshopbyid?shopId=1';
	 var shopManageUrl = '/myo2o/shop/shopedit';
	 var productManageUrl='/myo2o/shop/productmanage';
	//var initUrl = '/myo2o/shop/getshopinitinfo';
	//var editShopUrl = '/myo2o/shop/registershop';
	if (isEdit) {
	
		shopManageUrl = '/myo2o/shop/shopedit?shopId=' + shopId;
		productManageUrl='/myo2o/shop/productmanage?shopId='+shopId;
	}
	
	

	$('#submit').click(function() {
		window.location.href =shopManageUrl;
	});
	$('#submit2').click(function() {
		window.location.href =productManageUrl;
	});
	$('#submit8').click(function() {
		window.location.href ='/myo2o/shop/shopauthmanage';
	});

});