package com.MohaddisMedia.UrduFatwa;

import android.provider.BaseColumns;

public class DBHelper {
    public static class KutubEntry implements BaseColumns {
        public static final String ID = "id";
        public static final String PARENT_ID = "parent_id";
        public static final String BOOK_TITLE = "book_title";
        public static final String BOOK_IMAGE = "book_image";
        public static final String QUESTION_COUNT = "question_count";
        public static final String HAS_PARTS = "has_parts";
        public static final String NO_OF_PARTS = "part_number";
        public static final String TABLE_NAME = "books";

    }

    public static class BabEntry implements BaseColumns {
        public static final String ID = "id";
        public static final String PARENT_ID = "parent_id";
        public static final String BOOK_ID = "book_id";
        public static final String JILD_TITLE = "title";
        public static final String QUESTION_COUNT = "question_count";
        public static final String TABLE_NAME = "chapters";

    }

    public static class FavouriteEntry implements BaseColumns {
        public static final String FATWA_ID = "fatwa_id";
        public static final String FATWA_NO = "fatwa_no";
        public static final String QUESTION = "question";
        public static final String ANSWER = "answer";
        public static final String IS_IMPORTANT = "is_important";
        public static final String IS_MISCELLANEOUS = "is_miscellaneous";
        public static final String TYPE = "type";
        public static final String CREATION_DATE = "created_at";
        public static final String VIEW_COUNT = "view_count";
        public static final String TABLE_NAME = "Favourite";

    }


    public static class QuestionLink implements BaseColumns {
        public static final String ID = "id";
        public static final String QUESTION_ID = "question_id";
        public static final String BOOK_ID = "book_id";
        public static final String CHAPTER_ID = "chapter_id";
        public static final String TABLE_NAME = "question_links";


    }
    public static class TopicLinkEntry implements BaseColumns {
        public static final String ID = "id";
        public static final String QUESTION_ID = "question_id";
        public static final String CATEGORY_ID = "category_id";
        public static final String CREATED_AT = "created_at";
        public static final String TABLE_NAME = "question_categories";



    }

    public static class TopicEntry implements BaseColumns {
        public static final String ID = "id";
        public static final String PARENT_ID = "parent_id";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String CREATED_AT = "created_at";
        public static final String TABLE_NAME = "categories";
        public static final String QUESTION_COUNT = "question_count";



    }

    public static class FatwaEntry implements BaseColumns {
        public static final String ID = "id";
        public static final String FATWA_NO = "fatwa_no";
        public static final String QUESTION = "question";
        public static final String ANSWER = "answer";
        public static final String IS_IMPORTANT = "is_important";
        public static final String IS_MISCELLANEOUS = "is_miscellaneous";
        public static final String TYPE = "type";
        public static final String CREATION_DATE = "created_at";
        public static final String VIEW_COUNT = "view_count";
        public static final String TABLE_NAME = "questions";

    }
}
