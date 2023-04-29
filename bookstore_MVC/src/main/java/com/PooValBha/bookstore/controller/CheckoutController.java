package com.PooValBha.bookstore.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.PooValBha.bookstore.model.Book;
import com.PooValBha.bookstore.model.Customer;
import com.PooValBha.bookstore.service.BillingService;
import com.PooValBha.bookstore.service.ShoppingCartService;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

	private final BillingService billingService;

	private final ShoppingCartService shoppingCartService;

	public CheckoutController(BillingService billingService, ShoppingCartService shoppingCartService) {
		this.billingService = billingService;
		
		this.shoppingCartService = shoppingCartService;
	}

	@GetMapping(value = { "", "/" })
	public String checkout(Model model) {
		List<Book> cart = shoppingCartService.getCart();
		if (cart.isEmpty()) {
			return "redirect:/cart";
		}
		model.addAttribute("customer", new Customer());
		model.addAttribute("productsInCart", cart);
		model.addAttribute("totalPrice", shoppingCartService.totalPrice().toString());
		model.addAttribute("shippingCosts", shoppingCartService.getshippingCosts());
		return "checkout";
	}

	@PostMapping("/placeOrder")
	public String placeOrder(@Valid Customer customer, BindingResult result, RedirectAttributes redirect) {
		if (result.hasErrors()) {
			return "/checkout";
		}
		billingService.createOrder(customer, shoppingCartService.getCart());
		shoppingCartService.emptyCart();
		redirect.addFlashAttribute("successMessage", "The order is confirmed.");
		return "redirect:/cart";
	}

}