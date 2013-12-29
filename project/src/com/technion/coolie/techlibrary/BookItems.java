package com.technion.coolie.techlibrary;

public class BookItems {

	public class LibraryElement {

		// TODO how to save the picture? !!!!!!
		//-- the fields can be public?
		// TODO - add a field which points on the appropriate book description
		public String id; //z30-doc-number
		public String name; //z13-title
		public String author; //z13-author
		public String library; //z30-sub-library
		public String type; //z30-material

		// constructors TODO - delete?
		public LibraryElement() {
			id = name = author = library = type = "";
		}

		public LibraryElement(String elementId ,String elementName, String elementAuthor,
				String elementType, String elementLibrary) {
			id = elementId;
			name = elementName;
			author = elementAuthor;
			library = elementLibrary;
			type = elementType;
		}

		// copyConstructor which gets a LibraryElement
		public LibraryElement(LibraryElement libElement) {
			name = libElement.name;
			author = libElement.author;
			library = libElement.library;
			type = libElement.type;
		}

	}

	// for the holds list
	public class HoldElement extends LibraryElement {
		//pick-up is z37-pickup-location/z30-sub-library
		public String createDate; //z37-request-date
		public String arrivalDate; //z37-hold-date
		public String queuePosition;//z37-sequence need also for canceling and future stuff?
		public String itemSequence; //z37-item-sequence need also for canceling and future stuff?
		public String endHoldDate; //z37-end-hold-date

		public HoldElement() {
			super();
			createDate = arrivalDate = queuePosition = endHoldDate = "";
		}

		public HoldElement(String elementId,String elementName, String elementAuthor,
				String elementType, String elementLibrary, String createDate,
				String arrivalDate, String queuePosition, String itemSequence, String endHoldDate) {
			super(elementId, elementName, elementAuthor, elementType, elementLibrary);
			this.createDate = createDate;
			this.arrivalDate = arrivalDate;
			this.queuePosition = queuePosition;
			this.endHoldDate = endHoldDate;
			this.itemSequence = itemSequence;
		}

	}

	// for the loan list
	public class LoanElement extends LibraryElement {

		public String dueDate; //z36-due-date

		public LoanElement() {
			super();
			dueDate = "";
		}

		public LoanElement(String elementId, String elementName, String elementAuthor,
				String elementType, String elementLibrary, String dueDate) {
			super(elementId, elementName, elementAuthor, elementType, elementLibrary);
			this.dueDate = dueDate; 
		}

	}

}
