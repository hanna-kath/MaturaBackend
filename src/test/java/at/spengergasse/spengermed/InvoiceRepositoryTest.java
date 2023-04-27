package at.spengergasse.spengermed;

import at.spengergasse.spengermed.model.*;
import at.spengergasse.spengermed.repository.InvoiceRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class InvoiceRepositoryTest {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Test
    @Transactional
    public void testSaveLoadOneInvoice() {
        Invoice iv = returnOneInvoice();
        Invoice savedIv = invoiceRepository.save(iv);
        Optional<Invoice> loadedPrOptional = invoiceRepository.findById(UUID.fromString(String.valueOf(savedIv.getId())));
        Invoice loadedPr = loadedPrOptional.get();

        assertEquals(iv.getStatus(), loadedPr.getStatus());
        assertEquals(iv.getCancelledReason(), loadedPr.getCancelledReason());
        assertEquals(iv.getType(), loadedPr.getType());
        assertEquals(iv.getSubject(), loadedPr.getSubject());
        assertEquals(iv.getRecipient(), loadedPr.getRecipient());
        assertEquals(iv.getDate(), loadedPr.getDate());
        assertEquals(iv.getCreation(), loadedPr.getCreation());
        assertEquals(iv.getPeriodDate(), loadedPr.getPeriodDate());
        assertEquals(iv.getPeriodPeriod(), loadedPr.getPeriodPeriod());
        assertEquals(iv.getIssuer(), loadedPr.getIssuer());
        assertEquals(iv.getAccount(), loadedPr.getAccount());
        assertEquals(iv.getTotalNet(), loadedPr.getTotalGross());
        assertEquals(iv.getTotalGross(), loadedPr.getTotalGross());
        assertEquals(iv.getPaymentTerms(), loadedPr.getPaymentTerms());

        // asserts that a specified condition is true
        assertTrue(CollectionUtils.isEqualCollection(iv.getIdentifier(), loadedPr.getIdentifier()));
        assertTrue(CollectionUtils.isEqualCollection(iv.getParticipant(), loadedPr.getParticipant()));
        assertTrue(CollectionUtils.isEqualCollection(iv.getLineItem(), loadedPr.getLineItem()));
        assertTrue(CollectionUtils.isEqualCollection(iv.getTotalPriceComponent(), loadedPr.getTotalPriceComponent()));
        assertTrue(CollectionUtils.isEqualCollection(iv.getNote(), loadedPr.getNote()));
    }

    @Test
    @Transactional
    public void testDeleteOneInvoice() {
        Invoice iv = returnOneInvoice();
        Invoice savedIv = invoiceRepository.save(iv);
        invoiceRepository.deleteById(UUID.fromString(String.valueOf(savedIv.getId())));
        assertTrue(invoiceRepository.findById(UUID.fromString(String.valueOf(savedIv.getId()))).isEmpty());
    }

    @Test
    @Transactional
    public void testUpdateOnePractitioner() {
        Invoice iv = returnOneInvoice();
        Invoice savedEmptyIv = invoiceRepository.save(new Invoice());
        savedEmptyIv.setIdentifier(iv.getIdentifier());
        savedEmptyIv.setStatus(iv.getStatus());
        savedEmptyIv.setCancelledReason(iv.getCancelledReason());
        savedEmptyIv.setType(iv.getType());
        savedEmptyIv.setSubject(iv.getSubject());
        savedEmptyIv.setRecipient(iv.getRecipient());
        savedEmptyIv.setDate(iv.getDate());
        savedEmptyIv.setCreation(iv.getCreation());
        savedEmptyIv.setPeriodPeriod(iv.getPeriodPeriod());
        savedEmptyIv.setPeriodDate(iv.getPeriodDate());
        savedEmptyIv.setIssuer(iv.getIssuer());
        savedEmptyIv.setAccount(iv.getAccount());
        savedEmptyIv.setTotalPriceComponent(iv.getTotalPriceComponent());
        savedEmptyIv.setTotalNet(iv.getTotalNet());
        savedEmptyIv.setTotalGross(iv.getTotalGross());
        savedEmptyIv.setPaymentTerms(iv.getPaymentTerms());
        savedEmptyIv.setLineItem(iv.getLineItem());
        savedEmptyIv.setParticipant(iv.getParticipant());
        savedEmptyIv.setNote(iv.getNote());
    }

    public static Invoice returnOneInvoice() {

        List<Identifier> identifiers = new ArrayList<>();
        identifiers.add(Identifier.builder()
                .value("00000000-0000-0000-abcd-00000000no12")
                .type(CodeableConcept.builder().build())
                .use(Identifier.UseCode.temp)
                .build());

        List<Coding> codings = new ArrayList<>();
        codings.add(new Coding("System", "8.0.2", "code", "<div>...</div>", true));

        CodeableConcept types = CodeableConcept.builder()
                .coding(codings)
                .text("<div></div>")
                .build();

        Reference subjects = Reference.builder()
                .reference("Invoice/1")
                .display("Consultation")
                .build();

        Reference recipients = Reference.builder()
                .display("Termin")
                .reference("Invoice/2")
                .type("Untersuchung")
                .build();

        Period p = Period.builder()
                .start(LocalDateTime.of(2023, 06, 23, 3, 45, 0,0))
                .end(LocalDateTime.of(2023, 07, 29, 4, 12, 0,0))
                .build();

        Reference actor = Reference.builder()
                .reference("Invoice/3")
                .display("Attending")
                .build();

        List<Participant> participants = new ArrayList<>();
        participants.add(Participant.builder()
                .period(new Period(LocalDateTime.of(2022, 04,12,8,0,0),
                        LocalDateTime.of(2022,04,14,9,0,0)))
                .individual(actor)
                .build());

        Reference issuers = Reference.builder()
                .reference("Invoice/4")
                .display("not Attending")
                .build();

        Reference accounts = Reference.builder()
                .reference("Invoice/4")
                .display("not Attending")
                .build();

        CodeableConcept concept = CodeableConcept.builder()
                .coding(codings)
                .text("")
                .build();

        Money money = Money.builder()
                .value(262)
                .currency(Money.CurrencyCode.USD)
                .build();

        List<MonetaryComponent> monetaryComponents = new ArrayList<>();
        monetaryComponents.add(MonetaryComponent.builder()
                .code(concept)
                .type(MonetaryComponent.code.base)
                .factor(1234)
                .amount(money)
                .build());

        List<LineItem> lineItems = new ArrayList<>();
        lineItems.add(LineItem.builder()
                .sequence(12)
                .serviceddate(new Date())
                .servicedPeriod(p)
                .chargeItemReference(accounts)
                .priceComponent(monetaryComponents)
                .build());

        List<Annotation> annotations = new ArrayList<>();
        annotations.add(Annotation.builder()
                .authorString("Author")
                .time(LocalDateTime.of(2023,04,26,15,28))
                .text("")
                .build());

        Invoice iv = Invoice.builder()
                .identifier(identifiers)
                .status(Invoice.code.cancelled)
                .cancelledReason("Reason")
                .type(types)
                .subject(subjects)
                .recipient(recipients)
                .date(LocalDateTime.of(2023,04,24,22,28,00,00))
                .creation(LocalDateTime.of(2023,04,24,22,48,00,00))
                .periodDate(LocalDate.of(2023,04,02))
                .periodPeriod(p)
                .participant(participants)
                .issuer(issuers)
                .account(accounts)
                .lineItem(lineItems)
                .totalPriceComponent(monetaryComponents)
                .totalNet(money)
                .totalGross(money)
                .paymentTerms("Payment Details")
                .note(annotations)
                .build();

        return iv;
    }
}
