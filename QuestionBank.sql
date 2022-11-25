use eRecruitment
go

-- Marketing

INSERT INTO [Question]
VALUES
('Q001','Which of the following statements is correct?',1),
('Q002','The term marketing refers to:',1),
('Q003','What is a person who uses business products called?',1),
('Q004','What activity is geared around understanding and communicating with the customer to help in the design, development, delivery, and determination of the value inherent in the offering?',1),
('Q005','According to Leone and Shultz (1980), the law-like generalization 1 explains that ___________ has a direct and positive influence on total industry (market) sales.',1),
('Q006','What is about how the offering''s benefits and features are communicated to the potential buyer?',1),
('Q007','In relationship marketing firms focus on __________ relationships with __________.',1),
('Q008','What is about the concern with creating superior value by continuously developing and redeveloping offerings to meet customer needs?',1),
('Q009','Joint creation of value, in which customers take part in an active dialogue and co-construct personalized experiences, is referred to as:',1),
('Q010','According to the Charted Institute of Marketing, which of the following is not a professional marketing competency?',1),
('Q011','Which of the following is not an element of the marketing mix?',1),
('Q012','The term ''marketing mix'' describes:',1),
('Q013','Newsletters, catalogues, and invitations to organization-sponsored events are most closely associated with the marketing mix activity of:',1),
('Q014','The way in which the product is delivered to meet the customers'' needs refers to:',1),
('Q015','The ______________is concerned with ideas of the ''marketing mix'' and the 4Ps:',1),
('Q016','In public sector markets, the ____________ is bound by strict legal guidelines for contracts valued over a set amount.',1),
('Q017','The ____________ delivers to us a wide array of offerings, either directly or indirectly, through business markets, to serve our wants and needs.',1),
('Q018','A market orientation recognizes that:',1),
('Q019','Four competing philosophies strongly influence the role of marketing and marketing activities within an organization. Which if the following is not a component of market orientation?',1),
('Q020','In order for exchange to occur: ',1)

INSERT INTO [Option] ([q_id], [content], [isCorrect])
VALUES
('Q001','Marketing is the term used to refer only to the sales function within a firm.',0),
('Q001','Marketing managers don''t usually get involved in production or distribution decisions.',0),
('Q001','Marketing is an activity that considers only the needs of the organization; not the needs of society as a whole.',0),
('Q001','Marketing is the activity, set of institutions, and processes for creating, communicating, delivering, and exchanging offerings that have value for customers, clients, partners, and society at large.*',1),

('Q002','new product concepts and improvements.',0),
('Q002','advertising and promotion activities.',0),
('Q002','a philosophy that stresses customer value and satisfaction.*',1),
('Q002','planning sales campaigns.',0),

('Q003','Customer.',0),
('Q003','Agency.',0),
('Q003','Consumer.*',1),
('Q003','Wrecker.',0),

('Q004','production ',0),
('Q004','sales',0),
('Q004','finance',0),
('Q004','marketing*',1),

('Q005','increased consumer demand.',0),
('Q005','advertising*',1),
('Q005','increase in urbanization. ',0),
('Q005','elasticity ',0),

('Q006','product',0),
('Q006','place',0),
('Q006','price',0),
('Q006','promtion*',1),

('Q007','short-term; customers and stakeholders ',0),
('Q007','long-term; customers and stakeholders *',1),
('Q007','short-term; customers',0),
('Q007','long-term; customers ',0),

('Q008','interfunctional coordination ',0),
('Q008','competitor orientation.',0),
('Q008','production orientation.',0),
('Q008','customer orientation*',1),

('Q009','interactive marketing. ',0),
('Q009','co-creation experience.*',1),
('Q009','relationship marketing.',0),
('Q009','organization marketing.',0),

('Q010','Brand.',0),
('Q010','Sales tactics.*',1),
('Q010','Digital Integration.',0),
('Q010','Customer Experience',0),

('Q011','Promotion.',0),
('Q011','Product.',0),
('Q011','Target market.*',1),
('Q011','Pricing.',0),

('Q012','a composite analysis of all environmental factors inside and outside the firm.',0),
('Q012','a series of business decisions that aid in selling a product.',0),
('Q012','the relationship between a firm''s marketing strengths and its business weaknesses.',0),
('Q012','a blending of four strategic elements to satisfy specific target markets.*',1),

('Q013','Pricing',0),
('Q013','Distribution',0),
('Q013','Product development',0),
('Q013','Promotion*',1),

('Q014','new product concepts and improvements.',0),
('Q014','selling.',0),
('Q014','advertising and promotion activities.',0),
('Q014','place or distribution activities.*',1),

('Q015','consumer services perspective.',0),
('Q015','business-to-business perspective.',0),
('Q015','societal perspective.',0),
('Q015','consumer goods perspective.*',1),

('Q016','promotional mix.',0),
('Q016','logistics function.',0),
('Q016','procurement process.*',1),
('Q016','aggregate marketing system.',0),

('Q017','aggregate marketing system*',1),
('Q017','planning excellence',0),
('Q017','a quality rift',0),
('Q017','a value line',0),

('Q018','price is the most important variable for customers.',0),
('Q018','market intelligence relating to current and future customer needs is important.*',1),
('Q018','selling and marketing are essentially the same things.',0),
('Q018','sales depend predominantly on an aggressive sales force.',0),

('Q019','Customer orientation.',0),
('Q019','Profitability orientation.',0),
('Q019','Marketing orientation.',0),
('Q019','Competitor orientation.*',1),

('Q020','a complex societal system must be involved.',0),
('Q020','organized marketing activities must also occur.',0),
('Q020','a profit-oriented organization must be involved.',0),
('Q020','each party must have something of value to the other party.',1)

-- Logistic
INSERT INTO [Question]
VALUES
('Q021','The warehousing function that combines the logistical flow of several small shipments to a specific market area is',2),
('Q022','The transportation model coordinated between railways and roadways is',2),
('Q023','The transportation system that is an outcome of the combination of air and roadways is',2),
('Q024','Booking of shipping space in advance is helpful to an exporter in',2),
('Q025','A mate’s receipt is',2),
('Q026','According to the Multimodal Transportation of Goods Act, a multimodal transport document cannot be',2),
('Q027','The following incoterm cannot be used for contracts providing for transportation of goods by sea',2),
('Q028','The incoterm providing or least responsibility to seller is',2),
('Q029','The group of incoterms under which the seller’s responsibility is to obtain freight paid transport document for the main carriage is',2),
('Q030','The price quoted by the seller for the product',2),
('Q031','Packaging performs two functions. ___________ and logistics.',2),
('Q032','The outcome of the independent business function era was',2),
('Q033','The outcome of limited integrated business function was',2),
('Q034','The outcome of internally integrated business function was',2),
('Q035','The outcome of externally integrated business function was',2),
('Q036','The period of existence of independent business function was',2),
('Q037','The non -logistical component of logistics mission',2),
('Q038','The service mission of logistics',2),
('Q039','Marketing and sales is ___________ in the generic value chain of logistics.',2),
('Q040','A well defined corporate vision',2)

INSERT INTO [Option] ([q_id], [content], [isCorrect])
VALUES
('Q021','Break bulk function.',0),
('Q021','Operational function.',0),
('Q021','Stockpiling function.',0),
('Q021','Consolidation function.*',1),

('Q022','Transship.',0),
('Q022','Airtruck.',0),
('Q022','Piggyback.*',1),
('Q022','Fishyback.',0),

('Q023','Transship.',0),
('Q023','Airtruck.*',1),
('Q023','Piggyback.',0),
('Q023','Fishyback.',0),

('Q024','Saving in freight charges.',0),
('Q024','Availing bank finance.',0),
('Q024','Getting priority on inland movement of cargo by all.*',1),
('Q024','None of the above.',0),

('Q025','A draft bill of lading.',0),
('Q025','A substitute bill of lading.',0),
('Q025','Bill of lading evidencing goods carried on deck.',0),
('Q025','None of the above.*',1),

('Q026','A bearer instrument.',0),
('Q026','An order instrument.',0),
('Q026','A non-negotiable instrument.',0),
('Q026','None of the above.*',1),

('Q027','CFR.',0),
('Q027','DDP.*',1),
('Q027','DES.',0),
('Q027','DEQ.',0),

('Q028','EXW.*',1),
('Q028','DDP.',0),
('Q028','FOB.',0),
('Q028','CIF.',0),

('Q029','E terms.',0),
('Q029','C terms.*',1),
('Q029','D terms.',0),
('Q029','F terms.',0),

('Q030','Will vary depending upon the incoterm chosen.*',1),
('Q030','Is irrespective of the incoterm.',0),
('Q030','Will be the base price.',0),
('Q030','None of the above.',0),

('Q031','Distribution.',0),
('Q031','Store keeping.',0),
('Q031','Material handling.',0),
('Q031','Marketing.*',1),

('Q032','aggressive preaching skill.*',1),
('Q032','price -based competition.',0),
('Q032','customer value and harmonious relation.',0),
('Q032','increased productivity, profitability and market share.',0),

('Q033','aggressive preaching skill.',0),
('Q033','price based competition.*',1),
('Q033','customer value and harmonious relation.',0),
('Q033','increased productivity, profitability and market share.',0),

('Q034','aggressive preaching skill.',0),
('Q034','price based competition.',0),
('Q034','customer value and harmonious relation.*',1),
('Q034','increased productivity, profitability and market share.',0),

('Q035','aggressive preaching skill.',0),
('Q035','price based competition.',0),
('Q035','customer value and harmonious relation.',0),
('Q035','increased productivity, profitability and market share.*',1),

('Q036','till 1950s.*',1),
('Q036','1960s to 1970s.',0),
('Q036','1980s.',0),
('Q036','1990s.',0),

('Q037','reflect the vision of top management.',0),
('Q037','deal with basic services required for delivering of goods.',0),
('Q037','refers to the value-added services offered.*',1),
('Q037','reflects the ability of firm to exploit market.',0),

('Q038','reflects the ability of firm to exploit market.ment.*',1),
('Q038','deal with basic services required for delivering of goods.',0),
('Q038','refers to the value-added services offered.',0),
('Q038','reflect the vision of top manage.',0),

('Q039','the only activity.*',1),
('Q039','a primary activity.',0),
('Q039','not an activity.',0),
('Q039','a support activity.',0),

('Q040','reflects the capability of the management to think beyond the current way.*',1),
('Q040','reflects the extent of use of logistical component for competitive advantage.',0),
('Q040','ensures effective implementation of logistics strategy.',0),
('Q040','deals with managing change.',0)

-- Data Science and Analytics
INSERT INTO [Question]
VALUES
('Q041','Identify the language which is used in data science?',3),
('Q042','Choose the correct components of data science.',3),
('Q043','Which of the following is not a part of the data science process?',3),
('Q044','Total groups in which data can be characterized is?',3),
('Q045','Choose whether the following statement is true or false:  Unstructured data is not organized',3),
('Q046','A column is a  _________- representation of data.',3),
('Q047','Choose whether the following statement is true or false: A data frame is an unstructured representation of data',3),
('Q048','Among the following identify the one in which dimensionality reduction reduces.',3),
('Q049','Machine learning is a subset of which of the following.',3),
('Q050','FIND-S algorithm ignores?',3),
('Q051','Full form of PAC is _________________',3),
('Q052','Total types of layer in radial basis function neural networks is ______',3),
('Q053','Choose whether true or false:  Decision tree cannot be used for clustering',3),
('Q054','Procedural Domain Knowledge in a rule-based system, is in the form of?',3),
('Q055','Which of the following architecture is also known as systolic arrays?',3),
('Q056','Machines running LISP are also called?',3),
('Q057','A hybrid Bayesian Network consists of?',3),
('Q058','Identify the key data science skills among the following',3),
('Q059','Raw data should be processed only one time. Is the following statement true or false?',3),
('Q060','Identify the revision control system on the following.',3)

INSERT INTO [Option] ([q_id], [content], [isCorrect])
VALUES
('Q041','C++',0),
('Q041','R',0),
('Q041','Java',0),
('Q041','Ruby*',1),

('Q042','domain expertise',0),
('Q042','data engineering',0),
('Q042','advanced computing',0),
('Q042','all of the above*',1),

('Q043','ommunication building*',1),
('Q043','operationalize',0),
('Q043','model planning',0),
('Q043','Discovery',0),

('Q044','1',0),
('Q044','2*',1),
('Q044','3',0),
('Q044','4',0),

('Q045','True*',1),
('Q045','False',0),
('Q045','maybe true or false',0),
('Q045','cannot be determined',0),

('Q046','Diagonal',0),
('Q046','Vertical*',1),
('Q046','Top',0),
('Q046','Horizontal',0),

('Q047','True',0),
('Q047','False',1),

('Q048','Performance',0),
('Q048','Entropy',0),
('Q048','Stochastics',0),
('Q048','Collinearity*',1),

('Q049','Artificial intelligencec*',1),
('Q049','Deep learning',0),
('Q049','Data learning',0),
('Q049','None of the above',0),

('Q050','Positive',0),
('Q050','Negative*',1),
('Q050','Both',0),
('Q050','None',0),

('Q051','Probably Approx Cost',0),
('Q051','Probably Approximate Correct*',1),
('Q051','Probability Approx Communication',0),
('Q051','Probably Approximate Computation',0),

('Q052','1',0),
('Q052','2',0),
('Q052','3*',1),
('Q052','4',0),

('Q053','True',0),
('Q053','False*',1),

('Q054','Meta-Rules',0),
('Q054','Control Rules',0),
('Q054','Production Rules*',1),
('Q054','None of the above',0),

('Q055','MISD*',1),
('Q055','SISD',0),
('Q055','SIMD',0),
('Q055','None of the above',0),

('Q056','A1 Workstations*',1),
('Q056','Time-Sharing Terminals',0),
('Q056','Both A and B',0),
('Q056','None of the above',0),

('Q057','Discrete Variables',0),
('Q057','Continuous Variables',0),
('Q057','Both A and B*',1),
('Q057','None of the above',0),

('Q058','data visualization',0),
('Q058','machine learning',0),
('Q058','Statistics',0),
('Q058','all of the above*',1),

('Q059','True',0),
('Q059','False*',1),

('Q060','Scipy',0),
('Q060','Numpy',0),
('Q060','Git*',1),
('Q060','Slidify',0)

--Information Technology
INSERT INTO [Question]
VALUES
('Q061','What is the process at the most detailed level of the data flow diagrams known as?',4),
('Q062','Total bits used by the IPv6 address is ________',4),
('Q063','Identify the language which is mainly used for Artificial Intelligence',4),
('Q064','Why is a firewall used in a computer?',4),
('Q065','The full form of DOM is?',4),
('Q066','How many levels are there in the architecture of the database?',4),
('Q067','Among the following which is not a database management software',4),
('Q068','Identify the total standard color names that HTML supports.',4),
('Q069','Choose the port number of FTP.',4),
('Q070','Total number of layers in OSI model is ___________',4),
('Q071','UNIX is written in which language?',4),
('Q072','Identify the different features of Big Data Analytics.',4),
('Q073','Which of the following is an extension of image file?',4),
('Q074','Dbase III is?',4),
('Q075','The main memory of a computer system is?',4),
('Q076','Identify among the following servers which allow LAN users to share data.',4),
('Q077','URL stands for _______________',4),
('Q078','SSLstands for _______________',4),
('Q079','Which of the following is not a SQL command?',4),
('Q080','Identify the range of byte data types in JavaScript',4)

INSERT INTO [Option] ([q_id], [content], [isCorrect])
VALUES
('Q061','interfaces',0),
('Q061','functional primitives*',1),
('Q061','data flow',0),
('Q061','transform descriptions',0),

('Q062','64 bit',0),
('Q062','256 bit',0),
('Q062','128 bit*',1),
('Q062','32 bit',0),

('Q063','Java',0),
('Q063','J2EE',0),
('Q063','Prolog*',1),
('Q063','C',0),

('Q064','Monitoring',0),
('Q064','Data transmission',0),
('Q064','Authentication',0),
('Q064','Security*',1),

('Q065','Document-oriented memory',0),
('Q065','Document object model*',1),
('Q065','Document object memory',0),
('Q065','None',0),

('Q066','2',0),
('Q066','3*',1),
('Q066','4',0),
('Q066','5',0),

('Q067','MySQL',0),
('Q067','COBOL*',1),
('Q067','Sybase',0),
('Q067','Oracle',0),

('Q068','30',0),
('Q068','70',0),
('Q068','140*',1),
('Q068','120',0),

('Q069','23',0),
('Q069','21*',1),
('Q069','110',0),
('Q069','143',0),

('Q070','5',0),
('Q070','7*',1),
('Q070','9',0),
('Q070','11',0),

('Q071','C#',0),
('Q071','C++',0),
('Q071','C*',1),
('Q071','.Net',0),

('Q072','Open source',0),
('Q072','Data recovery',0),
('Q072','Scalability',0),
('Q072','All of the above*',1),

('Q073','.mkv',0),
('Q073','.gif*',1),
('Q073','.txt',0),
('Q073','.vdf',0),

('Q074','Hardware',0),
('Q074','Firmware',0),
('Q074','Application software*',1),
('Q074','operating system',0),

('Q075','Non-volatile',0),
('Q075','Volatile*',1),
('Q075','Restricted',0),
('Q075','Unrestricted',0),

('Q076','Communication server',0),
('Q076','Point server',0),
('Q076','Data server',0),
('Q076','File server*',1),

('Q077','Uninterrupted data locator',0),
('Q077','Uninterrupted record locator',0),
('Q077','Uniform record locator',0),
('Q077','Uniform resource locator*',1),

('Q078','secure socket layer*',1),
('Q078','secure secret level',0),
('Q078','secure system level',0),
('Q078','section security layer',0),

('Q079','DELETE*',1),
('Q079','ORDER BY',0),
('Q079','SELECT',0),
('Q079','WHERE',0),

('Q080','-10 to 9',0),
('Q080','-128 to 127*',1),
('Q080','-32768 to 32767',0),
('Q080','-2147483648 to 2147483647',0)

--Graphic Design
INSERT INTO [Question]
VALUES
('Q081','______ is an ancient Greek ideal ratio that can be expressed in numbers as 0.618034:1',5),
('Q082','________ is a false visual perception seeing something other than what is really there',5),
('Q083','Which one shows the pattern?',5),
('Q084','_______is the process of organizing and composing words and images to create a message',5),
('Q085',' _____ a technique in which a design is incised in a plate of metal, wood, or plastic. a print is then made from the plate',5),
('Q086',' ____that indicates areas between,around, above,below, or within something',5),
('Q087','______ is the area of a picture or design that appears to be closest to the viewer',5),
('Q088','______is a closed 2 dimension area',5),
('Q089','______ refers to 2 dynamics in a work of art that parts of things within a work of art are the right size in relationship to another.',5),
('Q090','______ is a visual journey along a page created by the careful placement of lines, shapes or forms, and colors',5),
('Q091','____ is a color scheme that uses colors that sit side by side on the color wheel and have a common hue',5),
('Q092','____ is a basic element repeated to create a pattern',5),
('Q093','______durable metal type invented by Johann Gutenberg, made from a combination of lead, tin, and antimony',5),
('Q094','______the characterization of a color as either warm or cool',5),
('Q095','_____is a the element of art that descries or refer to the relative between space is a the element of art that refers to the emptiness of the area between around or above below or with objects',5),
('Q096','______line, shape, form, color, value, space, and texture',5),
('Q097','______are the rules for dynamical that are used to help organize the elements of art the principle',5),
('Q098','Creates a very causal or natural effect is ______?',5),
('Q099','_____ is the act of organizing the elements of an artwork into a harmoniously unified whole',5),
('Q100','____ are simple signatures carved in wood, dipped in ink, and pressed onto paper or canvas – to identify their work',5)

INSERT INTO [Option] ([q_id], [content], [isCorrect])
VALUES
('Q081','intensity',0),
('Q081','golden Section*',1),
('Q081','color scheme',0),
('Q081','proportion',0),

('Q082','porportion',0),
('Q082','illusion',0),
('Q082','Graphic design',0),
('Q082','optical illusion*',1),

('Q083','template*',1),
('Q083','tonility',0),
('Q083','texture',0),
('Q083','temperature',0),

('Q084','optical illusion',0),
('Q084','expression',0),
('Q084','illusion',0),
('Q084','graphic design*',1),

('Q085','haramony',0),
('Q085','engravings*',1),
('Q085','insignia',0),
('Q085','branding',0),

('Q086','space*',1),
('Q086','hue',0),
('Q086','value',0),
('Q086','balance',0),

('Q087','porportion',0),
('Q087','foreground*',1),
('Q087','form',0),
('Q087','background',0),

('Q088','value',0),
('Q088','shape*',1),
('Q088','space',0),
('Q088','shade',0),

('Q089','porportion*',1),
('Q089','color',0),
('Q089','composition',0),
('Q089','expression',0),

('Q090','visual path*',1),
('Q090','template',0),
('Q090','Gestalt',0),
('Q090','space',0),

('Q091','Color scheme',0),
('Q091','Triad color scheme',0),
('Q091','Monochromatic color scheme',0),
('Q091','Analogous color scheme*',1),

('Q092','shape',0),
('Q092','form',0),
('Q092','motif*',1),
('Q092','hue',0),

('Q093','movable type*',1),
('Q093','logotype',0),
('Q093','value',0),
('Q093','tonility',0),

('Q094','template',0),
('Q094','temperature*',1),
('Q094','trademark',0),
('Q094','texture',0),

('Q095','value*',1),
('Q095','space',0),
('Q095','shade',0),
('Q095','line',0),

('Q096','The elements of art*',1),
('Q096','Temperature',0),
('Q096','3 Components of color',0),
('Q096','The elements of art/design',0),

('Q097','Principles of design*',1),
('Q097','The elements of art/design',0),
('Q097','Graphic design',0),
('Q097','optical illusion',0),

('Q098','Asymmetry balance*',1),
('Q098','balance',0),
('Q098','after image',0),
('Q098','Primary colors',0),

('Q099','expression',0),
('Q099','composition*',1),
('Q099','motif',0),
('Q099','porportion',0),

('Q100','watermark',0),
('Q100','trademark',0),
('Q100','closure',0),
('Q100','chop marks',1)

--Engineering
INSERT INTO [Question]
VALUES
('Q101','Representative fraction is the ____________________',6),
('Q102','The scale of a drawing is given as 1:20. What is the representative fraction?',6),
('Q103','The scale of a drawing is given as 15:1. What is the representative fraction?',6),
('Q104','The length of the drawing is 50 mm, the scale is given as 1:5. Find the actual length.',6),
('Q105','The actual length is 1m. The length of the drawing is 5cm. Find the representative factor.',6),
('Q106','The representative factor is 4. The actual length is 20 mm. Find the length of the drawing.',6),
('Q107','A machine part is drawn two times with different scales. The ratio of 1st drawing’s R.F. to 2nd drawing R.F. with respect to the actual object is found to be 2. The length of the second drawing is 10 mm. Find the 1st drawing length.',6),
('Q108','The length of the drawing is 20 cm, the scale is given as 2:1. Find the actual length.',6),
('Q109','The actual length is 1cm. The length of the drawing is 30 cm. Find the representative factor.',6),
('Q110','The representative factor is 0.02. The actual length is 50 cm. Find the length of drawing.',6),
('Q111','The ratio of a length of an actual object to the length of drawing is given as 5. Find the scale and R.F. (Representative factor).',6),
('Q112','The representative factor is 2, the drawing length is 100 mm. Find the actual length.',6),
('Q113','The representative factor is 0.5, the drawing length is 10 cm. Find the actual length.',6),
('Q114','The ratio of height to length of an arrow in dimensioning is ___',6),
('Q115','Dimensioning doesn’t represent _____',6),
('Q116','Which is the wrong statement regarding dimensions?',6),
('Q117','Dimension lines should be drawn at least ________mm away from the outlines and from each',6),
('Q118','Two types of dimensions needed on a drawing are: i) size or functional dimensions and ii) location or datum dimensions.',6),
('Q119','An outline or a centre line should be used as a dimension line.',6),
('Q120',' Which is the wrong statement from the below options?',6)

INSERT INTO [Option] ([q_id], [content], [isCorrect])
VALUES
('Q101','ratio of the length in drawing to the actual length*',1),
('Q101','ratio of the actual length to the length in drawing',0),
('Q101','reciprocal of actual length',0),
('Q101','square of the length in drawing',0),

('Q102','20',0),
('Q102','1/20*',1),
('Q102','0.5',0),
('Q102','0.02',0),

('Q103','15*',1),
('Q103','0.15',0),
('Q103','1.5',0),
('Q103','1/15',0),

('Q104','50 cm',0),
('Q104','10 cm',0),
('Q104','25 cm*',1),
('Q104','10 mm',0),

('Q105','1/5',0),
('Q105','20',0),
('Q105','1/20*',1),
('Q105','5',0),

('Q106','5 cm',0),
('Q106','5 mm',0),
('Q106','0.2 mm',0),
('Q106','8 cm*',1),

('Q107','5 mm',0),
('Q107','200 mm',0),
('Q107','5 cm',0),
('Q107','2 cm*',1),

('Q108','50 cm',0),
('Q108','10 cm*',1),
('Q108','25 cm',0),
('Q108','10 mm',0),

('Q109','1/30',0),
('Q109','30*',1),
('Q109','0.03',0),
('Q109','15',0),

('Q110','10 cm',0),
('Q110','5 cm',0),
('Q110','0.25 cm',0),
('Q110','10 mm*',1),

('Q111','1:5, 1/5*',1),
('Q111','5:1, 1/5',0),
('Q111','1:5, 5',0),
('Q111','5:1, 5',0),

('Q112','20 cm',0),
('Q112','50 mm*',1),
('Q112','20 mm',0),
('Q112','50 cm',0),

('Q113','20 cm*',1),
('Q113','50 mm',0),
('Q113','20 mm',0),
('Q113','50 cm',0),

('Q114','1:2',0),
('Q114','1:3*',1),
('Q114','1:4',0),
('Q114','1:1.5',0),

('Q115','height',0),
('Q115','length',0),
('Q115','depth',0),
('Q115','material*',1),

('Q116','Every dimension must be given, but none should be given more than once',0),
('Q116','Every dimension should be written to the left side of the drawing*',1),
('Q116','Dimensions should be placed outside the views',0),
('Q116','A centre line should not be used as a dimension line',0),

('Q117','5',0),
('Q117','6',0),
('Q117','7',0),
('Q117','8*',1),

('Q118','True*',1),
('Q118','False',0),

('Q119','True',0),
('Q119','False*',1),

('Q120','As far as possible all dimensions should be given in millimeters, omitting the abbreviation mm',0),
('Q120','The height of the dimension figures should be from 3mm to 5mm',0),
('Q120','A zero must always precede the decimal point when the dimension is less than unity',0),
('Q120','A zero must always precede the decimal point when the dimension is less than unity*',1)