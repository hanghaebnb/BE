package com.cloneweek.hanghaebnb.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StatusMsgCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "회원을 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    BAD_ID_PASSWORD(HttpStatus.BAD_REQUEST, "아이디나 비밀번호 패턴이 맞지 않습니다."),
    ROOM_NOT_FOUND(HttpStatus.BAD_REQUEST, "숙소를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "댓글을 찾을 수 없습니다."),
    INVALID_USER(HttpStatus.BAD_REQUEST, "작성자만 삭제/수정할 수 있습니다."),
    FILE_UPLOAD_FAILED(HttpStatus.BAD_REQUEST, "파일 업로드 실패"),
    FILE_DELETE_FAILED(HttpStatus.BAD_REQUEST, "파일 삭제 실패"),


    /* 409 CONFLICT : Resource의 현재 상태와 충돌, 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "데이터가 이미 존재합니다."),
    ALREADY_CLICKED_LIKE(HttpStatus.CONFLICT, "이미 좋아요를 눌렀습니다"),
    ALREADY_CANCEL_LIKE(HttpStatus.CONFLICT, "이미 좋아요 취소를 눌렀습니다"),
    EXIST_USER(HttpStatus.CONFLICT, "중복된 이메일입니다."),
    EXIST_NICK(HttpStatus.CONFLICT, "중복된 닉네임입니다."),


    /* 200 SUCCESS */
    SIGN_UP(HttpStatus.OK, "회원가입에 성공했습니다."),
    LOG_IN(HttpStatus.OK, "로그인에 성공했습니다"),
    LIKE(HttpStatus.OK, "좋아요 성공"),
    CANCEL_LIKE(HttpStatus.OK, "좋아요 취소"),
    DELETE_POST(HttpStatus.OK, "숙소를 삭제하였습니다"),
    DONE_POST(HttpStatus.OK, "숙소 등록 완료"),
    DELETE_COMMENT(HttpStatus.OK, "댓글을 삭제하였습니다"),
    NICKNAME(HttpStatus.OK, "사용 가능한 닉네임입니다."),
    EMAIL(HttpStatus.OK, "사용 가능한 이메일입니다."),
    EMAIL_CONFIRM(HttpStatus.OK, "해당 이메일로 회원가입 가능합니다."),
    UPDATE(HttpStatus.OK, "게시글 수정 완료");


    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
//    INVALID_AUTH_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다."),
//    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "권한 정보가 없는 토큰입니다."),
//    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "존재하지 않는 유저입니다."),

    /* 404 NOT_FOUND : Resource를 찾을 수 없음 */
//    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다."),
//    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "로그아웃 한 유저입니다."),
//    NOT_FOLLOW(HttpStatus.NOT_FOUND, "팔로우 중이지 않습니다"),

    /* 500 INTERNAL_SERVER_ERROR */
//    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버가 없습니다.");


    private final HttpStatus httpStatus;
    private final String detail;
}

