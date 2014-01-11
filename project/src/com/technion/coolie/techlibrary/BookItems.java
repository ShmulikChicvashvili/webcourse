package com.technion.coolie.techlibrary;

public class BookItems {

	public class LibraryElement {

		private static final int NUM_OF_ELEMENTS = 8;

		// TODO how to save the picture? !!!!!!
		// -- the fields can be public?
		// TODO - add a field which points on the appropriate book description
		public String id; // z30-doc-number
		public String name; // z13-title
		public String author; // z13-author
		public String library; // z30-sub-library
		// public String type; // z30-material

		public String isbn;
		public String language;

		public String publisher;

		public String edition;

		// constructors TODO - delete?
		public LibraryElement() {
			id = name = author = library = isbn = language = edition = publisher = "";
		}

		public LibraryElement(String elementId, String elementName,
				String elementAuthor, String elementLibrary,
				String elementISBN, String elementLanguage,
				String elementEdition, String elementPublisher) {
			id = elementId;
			name = elementName;
			author = elementAuthor;
			library = elementLibrary;
			// type = elementType;
			isbn = elementISBN;
			language = elementLanguage;
			edition = elementEdition;
			publisher = elementPublisher;
		}

		// copyConstructor which gets a LibraryElement
		public LibraryElement(LibraryElement libElement) {
			name = libElement.name;
			author = libElement.author;
			library = libElement.library;
			// type = libElement.type;
			isbn = libElement.isbn;
			language = libElement.language;
			edition = libElement.edition;
			publisher = libElement.publisher;
		}

		public String[] toArray() {
			String[] array = { id, name, author, library, isbn, language,
					edition, publisher };
			return array;
		}

		public LibraryElement arrayToLibElement(String[] array) {
			if (array.length != NUM_OF_ELEMENTS) {
				return null;
			}
			LibraryElement le = new LibraryElement(array[0], array[1],
					array[2], array[3], array[4], array[5], array[6], array[7]);
			return le;
		}

	}

	// for the holds list
	public class HoldElement extends LibraryElement {
		// pick-up is z37-pickup-location/z30-sub-library
		public String createDate; // z37-request-date
		public String arrivalDate; // z37-hold-date
		public String queuePosition;// z37-sequence need also for canceling and
									// future stuff?
		public String itemSequence; // z37-item-sequence need also for canceling
									// and future stuff?
		public String endHoldDate; // z37-end-hold-date

		public HoldElement() {
			super();
			createDate = arrivalDate = queuePosition = endHoldDate = "";
		}

		public HoldElement(String elementId, String elementName,
				String elementAuthor, String elementLibrary, String createDate,
				String arrivalDate, String queuePosition, String itemSequence,
				String endHoldDate) {
			super(elementId, elementName, elementAuthor, elementLibrary, "",
					"", "", "");
			this.createDate = createDate;
			this.arrivalDate = arrivalDate;
			this.queuePosition = queuePosition;
			this.endHoldDate = endHoldDate;
			this.itemSequence = itemSequence;
		}

	}

	// for the loan list
	public class LoanElement extends LibraryElement {

		public String dueDate; // z36-due-date

		public LoanElement() {
			super();
			dueDate = "";
		}

		public LoanElement(String elementId, String elementName,
				String elementAuthor, String elementLibrary, String dueDate) {
			super(elementId, elementName, elementAuthor, elementLibrary, "",
					"", "", "");
			this.dueDate = dueDate;
		}

	}

}
