package com.bkc.delivery.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bkc.admin.board.banner.vo.CheckVO;
import com.bkc.admin.board.businessInformation.service.BusinessInformationService;
import com.bkc.admin.board.businessInformation.vo.BusinessInformationVO;
import com.bkc.delivery.service.CautionService;
import com.bkc.delivery.service.DvProductService;
import com.bkc.delivery.vo.CautionVO;
import com.bkc.delivery.vo.DvProductVO;
import com.bkc.menuInform.service.ProductService;
import com.bkc.menuInform.vo.ProductVO;
import com.bkc.user.service.CouponService;
import com.bkc.user.service.UserCouponService;
import com.bkc.user.service.UserService;
import com.bkc.user.vo.CartVO;
import com.bkc.user.vo.GuestVO;
import com.bkc.user.vo.UserCouponVO;
import com.bkc.user.vo.UserVO;

@Controller
@RequestMapping(value = "/delivery", method = RequestMethod.GET)
public class DeliveryController {

	@Autowired
	private BusinessInformationService biService;

	@Autowired
	private UserService userService;

	@Autowired
	private CautionService cService;

	@Autowired
	private DvProductService pService;

	@Autowired
	private CouponService couponService;

	@Autowired
	private UserCouponService usercouponService;

	@Autowired
	private ProductService productService;

	// 카트리스트
	private CartVO cart = new CartVO(); // list로되어 있는 카트

	// 회원 주문 페이지로 이동
	@RequestMapping(value = "/delivery.do", method = RequestMethod.GET)
	public String delivery(CautionVO cautionVO, Model model,HttpSession session) {
		System.out.println("delivery 페이지 이동");

		// 유의사항 화면출력
		List<CautionVO> CautionList = cService.CautionList(cautionVO);
		model.addAttribute("CautionList", CautionList);

		// 현재 로그인한 사용자 추가
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails) principal;
		UserVO user = userService.getUserById(userDetails.getUsername());
		model.addAttribute("user", user);

		// 푸터추가
		BusinessInformationVO bi = biService.getBusinessInformation(1);
		model.addAttribute("bi", bi);

		// 치킨메뉴
		List<DvProductVO> chickendv = pService.getChickenMenudv();
		System.out.println(chickendv);
		model.addAttribute("chickendv", chickendv);

		// 사이드메뉴
		List<DvProductVO> sidedv = pService.getSideMenudv();
		System.out.println(sidedv);
		model.addAttribute("sidedv", sidedv);

		// 비어존
		List<DvProductVO> beerdv = pService.getBeerZonedv();
		System.out.println(beerdv);
		model.addAttribute("beerdv", beerdv);

		// new메뉴
		List<DvProductVO> newdv = pService.getNewdv();
		System.out.println(newdv);
		model.addAttribute("newdv", newdv);

		// 카트 추가
		CartVO cart = new CartVO();
		if (session.getAttribute("cart") == null) {} 
		else { cart = (CartVO) session.getAttribute("cart");}
		model.addAttribute("cart", cart);

		return "delivery/delivery";
	}

	// 주문내역 페이지로 이동
	@RequestMapping(value = "/orderList.do", method = RequestMethod.GET)
	public String orderList(Model model, HttpSession session) {
		System.out.println("회원 주문내역 페이지 이동");

		// 현재 로그인한 사용자 추가
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails) principal;
		UserVO user = userService.getUserById(userDetails.getUsername());
		model.addAttribute("user", user);

		// 푸터추가
		BusinessInformationVO bi = biService.getBusinessInformation(1);
		model.addAttribute("bi", bi);

		// 카트 추가
		CartVO cart = new CartVO();
		if (session.getAttribute("cart") == null) {} 
		else { cart = (CartVO) session.getAttribute("cart");}
		model.addAttribute("cart", cart);

		return "delivery/orderList";
	}

	// 주문상세 페이지로 이동
	@RequestMapping(value = "/orderDetail.do", method = RequestMethod.GET)
	public String orderDetail(Model model, HttpSession session) {
		System.out.println("회원 주문상세 페이지 이동");
		// 현재 로그인한 사용자 추가
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails) principal;
		UserVO user = userService.getUserById(userDetails.getUsername());
		model.addAttribute("user", user);

		// 푸터추가
		BusinessInformationVO bi = biService.getBusinessInformation(1);
		model.addAttribute("bi", bi);

		// 카트 추가
		CartVO cart = new CartVO();
		if (session.getAttribute("cart") == null) {} 
		else { cart = (CartVO) session.getAttribute("cart");}
		model.addAttribute("cart", cart);

		return "delivery/orderDetail";
	}

	// mybkc 페이지로 이동
	@RequestMapping(value = "/mybkc.do", method = RequestMethod.GET)
	public String mybkc(Model model, HttpSession session) {
		System.out.println("mybkc페이지로 이동");

		// 현재 로그인한 사용자 추가
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails) principal;
		UserVO user = userService.getUserById(userDetails.getUsername());
		model.addAttribute("user", user);

		// 쿠폰 넣기
		List<UserCouponVO> usercoupons = usercouponService.getUserHavingCouponDetail(user.getUserid());
		int couponcount = usercoupons.size();
		model.addAttribute("couponcount", couponcount);

		// 푸터추가
		BusinessInformationVO bi = biService.getBusinessInformation(1);
		model.addAttribute("bi", bi);

		// 카트 추가
		CartVO cart = new CartVO();
		if (session.getAttribute("cart") == null) {} 
		else { cart = (CartVO) session.getAttribute("cart");}
		model.addAttribute("cart", cart);

		return "delivery/mybkc";
	}

	// my쿠폰 페이지로 이동.
	@RequestMapping(value = "/mycoupon.do", method = RequestMethod.GET)
	public String mycoupon(Model model, HttpSession session) {
		System.out.println("mybkc페이지로 이동");

		// 푸터추가
		BusinessInformationVO bi = biService.getBusinessInformation(1);
		model.addAttribute("bi", bi);

		// 현재 로그인한 사용자 추가
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails) principal;
		UserVO user = userService.getUserById(userDetails.getUsername());
		model.addAttribute("user", user);

		// 쿠폰 넣기
		List<UserCouponVO> usercoupons = usercouponService.getUserHavingCouponDetail(user.getUserid());
		model.addAttribute("usercoupons", usercoupons);

		// 쿠폰 갯수 포내주기
		int couponcount = usercoupons.size();
		model.addAttribute("couponcount", couponcount);

		// 카트 추가
		CartVO cart = new CartVO();
		if (session.getAttribute("cart") == null) {} 
		else { cart = (CartVO) session.getAttribute("cart");}
		model.addAttribute("cart", cart);

		return "delivery/mycoupon";
	}

	// 유의사항 관리자 리스트
	@RequestMapping(value = "/admin/cautionList.ad", method = { RequestMethod.GET })
	public String CautionList(CautionVO cautionVO, Model model) {

		// 유의사항
		List<CautionVO> CautionList = cService.CautionList(cautionVO);
		model.addAttribute("CautionList", CautionList);
		return "/admin/delivery/cautionList";
	}

	// 유의사항 관리자 업로드 페이지
	@RequestMapping(value = "/admin/cautionUploadpage.ad", method = RequestMethod.GET)
	public String CautionUploadPage() {
		return "admin/delivery/cautionUpload";
	}

	// 유의사항 관리자 업로드 전송
	@RequestMapping(value = "/admin/CautionUpload.ad", method = RequestMethod.GET)
	public String CautionUpload(@RequestParam String content) throws IOException, PSQLException {

		CautionVO cautionVO = new CautionVO();
		cautionVO.setContent(content);

		cService.CautionInsert(cautionVO);

		return "redirect:/delivery/admin/cautionList.ad";
	}

	// 유의사항 관리자 수정화면
	@RequestMapping(value = "/admin/CautionDetail.ad", method = RequestMethod.GET)
	public String CautionDetail(Model model, @RequestParam("seq") int seq) {

		CautionVO cautionVO = cService.getCaution(seq);
		model.addAttribute("CautionVO", cautionVO);

		return "admin/delivery/cautionContent";
	}

	// 유의사항 관리 수정완료
	@RequestMapping(value = "/admin/CautionUpdate.ad", method = { RequestMethod.GET, RequestMethod.POST })
	public String modifyCaution(@RequestParam("content") String content, Model model, @RequestParam("seq") int seq) {

		CautionVO cautionVO = cService.getCaution(seq);
		cautionVO.setContent(content);
		cautionVO.setSeq(seq);

		cService.CautionUpdate(cautionVO);

		model.addAttribute("CautionVO", cautionVO);
		return "redirect:/delivery/admin/cautionList.ad";
	}

	// 유의사항 삭제
	@RequestMapping(value = "/admin/CautionDelete.ad", method = RequestMethod.GET)
	public String FaqDelete(Model model, @RequestParam("seq") int seq) {
		cService.CautionDelete(seq);
		System.out.println("삭제 완료");
		return "redirect:/delivery/admin/cautionList.ad";
	}

	// 카트에 담기 | 장바구니 세션값을 변경해주는 곳 
	@RequestMapping(value = "/cart.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String showCart(HttpSession session, Model model, @RequestParam(value = "seq", defaultValue = "0") int seq) {
		System.out.println(seq);

		CartVO cart = new CartVO();
		// seq값 없이 들어오는 경우
		if (seq == 0) {
			// 세션이 아예없다면,
			if (session.getAttribute("cart") == null) {} 
			else { // 세션이 있다면
				cart = (CartVO) session.getAttribute("cart");
			}
		}

		// seq값 가지고 들어오는 경우
		else {
			// 메뉴 가져옴.
			ProductVO product = productService.getMenuBySerial(seq);
			System.out.println(product.toString());

			// 처음 들어온 경우일경우
			if (session.getAttribute("cart") == null) {
				// 가져온 메뉴 카트에 추가
				cart.addFirst(seq, product);

				HashMap<Integer, ProductVO> products = cart.getProducts();
				cart.setProductCount(products.size());

				// 처음 .세션을 설정한다. 카트에는 하나만 들어간다.
				session.setAttribute("cart", cart);
				System.out.println("첫 카트");
			}

			// 세션
			else {
				// 세션에서 카트에 들어가 있는 걸 받아온다.
				cart = (CartVO) session.getAttribute("cart");

				// 카트갯수 ++ 처음이 아닌 경우 카트에 추가한다.
				// 카트에 메뉴를 추가시킨다.
				cart.addProduct(seq, product);

				HashMap<Integer, ProductVO> products = cart.getProducts();

				// 카트에 뭐가있는지 확인.
				Set keyset = products.keySet();
				System.out.println("Key set values are" + keyset);

				// 갯수넣기
				cart.setProductCount(products.size());
				session.setAttribute("cart", cart);
				System.out.println("첫 카트가 아닙니다.");
			}
		}

		// 현재 로그인한 사용자 추가
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails) principal;
		UserVO user = userService.getUserById(userDetails.getUsername());
		model.addAttribute("user", user);
		model.addAttribute("cart", cart);

		return "/delivery/cart";
	}

	/*
	 * 
	 * Guest 딜리버리
	 * 
	 * 
	 */

	// guest를 세션에 넣어두고 주문할 수 있도록 보낸다.
	@RequestMapping(value = "/guestDelivery", method = { RequestMethod.GET, RequestMethod.POST })
	public String goGuestDeliveryPage(Model model, HttpSession session) {
		String guest = (String) session.getAttribute("guest");
		model.addAttribute("guest", guest);
		return "guest/guestdelivery";
	}
}
