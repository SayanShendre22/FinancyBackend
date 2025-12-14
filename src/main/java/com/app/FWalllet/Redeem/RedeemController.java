package com.app.FWalllet.Redeem;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.DTO.RedeemReqDTO;
import com.app.DTO.RedeemResponseDTO;

@RestController
@RequestMapping("/coins/redeem")
@CrossOrigin(origins = "http://localhost:8082")
public class RedeemController {
	
	@Autowired
	private RedeemService redeemService;

	@PostMapping
	public ResponseEntity<?> create(@RequestBody RedeemReqDTO dto) {
	try {
			RedeemRequest req = redeemService.createRedeem(dto);
			return ResponseEntity.status(201).body(req);
		} catch (IllegalArgumentException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}

	@PostMapping("/process/{id}")
	public ResponseEntity<?> process(@PathVariable Long id) {
		try {
			RedeemRequest req = redeemService.processRedeem(id);
			return ResponseEntity.ok(req);
		} catch (Exception ex) {
			return ResponseEntity.status(500).body(ex.getMessage());
		}
	}
	
	@GetMapping("/getLast/{id}")
	public ResponseEntity<?> getLast(@PathVariable long id){
		try {
			var res = redeemService.getLastReq(id);
			List<RedeemRequest> listRes = new ArrayList<>();
			
			for(RedeemRequest rr : res) {
				listRes.add(rr);
			}

			
			return ResponseEntity.ok(res);
		} catch (Exception ex) {
			return ResponseEntity.status(500).body(ex.getMessage());
		}
	}

}
