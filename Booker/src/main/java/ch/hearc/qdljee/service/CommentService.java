package ch.hearc.qdljee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import ch.hearc.qdljee.Tools;
import ch.hearc.qdljee.dto.CommentDto;
import ch.hearc.qdljee.model.Comments;
import ch.hearc.qdljee.model.User;
import ch.hearc.qdljee.repository.CommentRepository;

public class CommentService {
	@Autowired
	CommentRepository commentRepository;

	public List<Comments> getAllComments() {
		List<Comments> comments = new ArrayList<Comments>();
		commentRepository.findAll().forEach(comment -> comments.add(comment));
		return comments;
	}

	public List<Comments> getAllCommentsForABook(Integer bookId) {
		List<Comments> all = getAllComments();
		List<Comments> bookComments = new ArrayList<Comments>();
		for (Comments comment : all) {
			if (comment.getBookId() == bookId) {
				bookComments.add(comment);
			}
		}
		return bookComments;
	}

	public Comments getCommentsById(int id) {
		return commentRepository.findById(id).get();
	}

	public void save(CommentDto comDto) {
		Comments com = new Comments(comDto.getData(), Tools.getCurrentUser(), comDto.getPublicationDate(),
				comDto.getBookId());
		commentRepository.save(com);
	}

	public void update(CommentDto comDto, String data, int id) throws Exception {
		Optional<Comments> com = commentRepository.findById(id);
		if (com.isEmpty())
			throw new Exception("no comment to update");
		com.get().addAttributes(comDto.getData(), Tools.getCurrentUser(), comDto.getPublicationDate());
		commentRepository.save(com.get());
	}

	public void delete(int id) {
		commentRepository.deleteById(id);
	}

}
