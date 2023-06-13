package ltr.org.capturenotes.repository;

import ltr.org.capturenotes.entity.CaptureNotesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICaptureNotesRepository extends JpaRepository<CaptureNotesEntity,Long> {
}
