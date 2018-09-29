package article.service;

public class DeleteRequest {
	private int userId;
	private int articleId;

	public DeleteRequest(int userId, int articleId) {
		this.userId = userId;
		this.articleId = articleId;

	}
	public int getUserId() {
		return userId;
	}
	public int getArticleId() {
		return articleId;
	}
	
}
