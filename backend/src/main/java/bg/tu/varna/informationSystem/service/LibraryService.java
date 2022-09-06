package bg.tu.varna.informationSystem.service;

import bg.tu.varna.informationSystem.dto.libraries.LibraryResponseDto;
import bg.tu.varna.informationSystem.entity.Library;
import bg.tu.varna.informationSystem.repository.LibraryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryService {
    private final LibraryRepository libraryRepository;
    private final ModelMapper modelMapper;

    public LibraryService(LibraryRepository libraryRepository, ModelMapper modelMapper) {
        this.libraryRepository = libraryRepository;
        this.modelMapper = modelMapper;
    }

    private void assignUserToLibrary(Long userId, Long libraryId) {
        libraryRepository.assignUserToLibrary(userId, libraryId);
    }

    public void assignUserToLibraries(Long userId, List<Long> libraries) {
        libraries.forEach(libraryId -> assignUserToLibrary(userId, libraryId));
    }

    public List<Library> getLibrariesByUserId(Long userId) {
        return libraryRepository.getLibrariesByUserId(userId);
    }

    public List<LibraryResponseDto> findByIds(List<Long> libraryIds) {
        return libraryRepository.findByIdIn(libraryIds)
                    .stream()
                    .map(this::convertToResponseDto)
                    .collect(Collectors.toList());
    }

    private LibraryResponseDto convertToResponseDto(Library library) {
        return modelMapper.map(library, LibraryResponseDto.class);
    }

    public List<LibraryResponseDto> findAll() {
        return libraryRepository.findAll().stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }
}
