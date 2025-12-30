package com.tanx.journal.Services;

import com.tanx.journal.Entity.JournalEntry;
import com.tanx.journal.Repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public JournalEntry saveEntry(JournalEntry journalEntry){
        return journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllEntries(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getEntryById(String id){
        return journalEntryRepository.findById(id);
    }

    public Optional<JournalEntry> deleteEntryById(String id){
        Optional<JournalEntry> entry = journalEntryRepository.findById(id);
        journalEntryRepository.deleteById(id);
        return entry;
    }
}
