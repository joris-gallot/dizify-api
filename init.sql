-- Eminem
INSERT INTO artists (id, name, image, description, updated_at, created_at)
  VALUES
    (1, 'Eminem', 'https://i.pravatar.cc/200',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.',
    '2020-11-13 11:36:02', '2020-11-13 11:36:02');

INSERT INTO albums (id, name, image, author_id, publication_date, updated_at, created_at)
  VALUES
    (1, 'Encore', 'https://picsum.photos/200', 1, '2004-11-13 11:36:02', '2020-11-13 11:36:02', '2020-11-13 11:36:02');
INSERT INTO titles (id, name, duration, author_id, album_id, updated_at, created_at)
  VALUES
    (1, 'Mosh', '00:05:17', 1, 1, '2020-11-13 11:36:02', '2020-11-13 11:36:02'),
    (2, 'Puke', '00:04:07', 1, 1, '2020-11-13 11:36:02', '2020-11-13 11:36:02'),
    (3, 'Rain Man', '00:05:13', 1, 1, '2020-11-13 11:36:02', '2020-11-13 11:36:02');


INSERT INTO albums (id, name, image, author_id, publication_date, updated_at, created_at)
  VALUES
    (2, 'Recovery', 'https://picsum.photos/200', 1,'2010-01-22 01:21:22', '2020-11-13 11:36:02', '2020-11-13 11:36:02');
INSERT INTO titles (id, name, duration, author_id, album_id, updated_at, created_at) 
  VALUES
    (4, 'On Fire', '00:03:33', 1, 2, '2020-11-13 11:36:02', '2020-11-13 11:36:02'),
    (5, 'W.T.P', '00:04:07', 1, 2, '2020-11-13 11:36:02', '2020-11-13 11:36:02'),
    (6, 'Not Afraid', '00:04:08', 1, 2, '2020-11-13 11:36:02', '2020-11-13 11:36:02'),
    (7, 'Seduction', '00:04:35', 1, 2, '2020-11-13 11:36:02', '2020-11-13 11:36:02'),
    (8, 'My Name Is', '00:04:32', 1, null, '2020-11-13 11:36:02', '2020-11-13 11:36:02'),
    (9, 'Guilty Conscience', '00:03:19', 1, null, '2020-11-13 11:36:02', '2020-11-13 11:36:02'),
    (10, 'Brain Damage', '00:03:36', 1, null, '2020-11-13 11:36:02', '2020-11-13 11:36:02'),
    (11, 'If I Had', '00:04:05', 1, null, '2020-11-13 11:36:02', '2020-11-13 11:36:02');


-- The Beatles
INSERT INTO artists (id, name, image, description, updated_at, created_at) 
  VALUES
    (2, 'The Beatles', 'https://i.pravatar.cc/200',
    'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit.',
    '2020-11-13 11:36:02', '2020-11-13 11:36:02');

INSERT INTO albums (id, name, image, author_id, publication_date, updated_at, created_at)
  VALUES
    (3, 'Abbey Road', 'https://picsum.photos/200', 1,'1969-11-13 11:36:02', '2020-11-13 11:36:02', '2020-11-13 11:36:02');
INSERT INTO titles (id, name, duration, author_id, album_id, updated_at, created_at) 
  VALUES
    (12, 'Oh! Darling', '00:03:27', 2, 3, '2020-11-13 11:36:02', '2020-11-13 11:36:02'),
    (13, 'Octopus’s Garden', '00:02:51', 2, 3, '2020-11-13 11:36:02', '2020-11-13 11:36:02'),
    (14, 'Because', '00:02:46', 2, 3, '2020-11-13 11:36:02', '2020-11-13 11:36:02'),
    (15, 'You Never Give Me Your Money', '00:04:02', 2, 3, '2020-11-13 11:36:02', '2020-11-13 11:36:02');


INSERT INTO albums (id, name, image, author_id, publication_date, updated_at, created_at)
  VALUES
    (4, 'Revolver', 'https://picsum.photos/200', 1,'1966-01-22 01:21:22', '2020-11-13 11:36:02', '2020-11-13 11:36:02');
INSERT INTO titles (id, name, duration, author_id, album_id, updated_at, created_at) 
  VALUES
    (16, 'Taxman', '00:02:41', 2, 4, '2020-11-13 11:36:02', '2020-11-13 11:36:02'),
    (17, 'Eleanor Rigby', '00:02:06', 2, 4, '2020-11-13 11:36:02', '2020-11-13 11:36:02'),
    (18, 'I’m Only Sleepin', '00:03:03', 2, 4, '2020-11-13 11:36:02', '2020-11-13 11:36:02'),
    (19, 'Love You To', '00:03:03', 2, 4, '2020-11-13 11:36:02', '2020-11-13 11:36:02'),
    (20, 'Yellow Submarine', '00:02:41', 2, 4, '2020-11-13 11:36:02', '2020-11-13 11:36:02'),
    (21, 'Two of Us', '00:03:06', 2, null, '2020-11-13 11:36:02', '2020-11-13 11:36:02'),
    (22, 'I Me Mine', '00:02:25', 2, null, '2020-11-13 11:36:02', '2020-11-13 11:36:02'),
    (23, 'Maggie Mae', '00:00:40', 2, null, '2020-11-13 11:36:02', '2020-11-13 11:36:02'),
    (24, 'The Long and Winding Road', '00:03:38', 2, null, '2020-11-13 11:36:02', '2020-11-13 11:36:02'),
    (25, 'One After 909', '00:02:53', 2, null, '2020-11-13 11:36:02', '2020-11-13 11:36:02');