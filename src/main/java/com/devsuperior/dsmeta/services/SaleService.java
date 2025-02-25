package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleSummaryDTO> findSummary(LocalDate minDate, LocalDate maxDate, Pageable pageable) {
		LocalDate today = LocalDate.now();

		LocalDate initialDate;

		if(minDate != null) {
			initialDate = minDate;
		} else if(minDate == null && maxDate != null) {
			initialDate = maxDate.minusYears(1);
		} else {
			initialDate = today.minusYears(1);
		}

		LocalDate finalDate = maxDate != null ? maxDate : today;

		return repository.searchSummaryByDate(initialDate, finalDate, pageable);
	}

	public Page<SaleReportDTO> findReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable) {
		LocalDate today = LocalDate.now();
		LocalDate initialDate;


		if(minDate != null) {
			initialDate = minDate;
		} else if(minDate == null && maxDate != null) {
			initialDate = maxDate.minusYears(1);
		} else {
			initialDate = today.minusYears(1);
		}

		LocalDate finalDate = maxDate != null ? maxDate : today;

		String searchName = (name != null && !name.trim().isEmpty()) ? name.trim() : null;

		return repository.searchReportByDate(initialDate, finalDate, searchName, pageable);
	}
}
