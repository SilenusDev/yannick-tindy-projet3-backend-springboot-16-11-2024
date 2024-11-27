-- Déchargement des données de la table `users`
INSERT INTO `users` (`id`, `email`, `name`, `password`, `role`,`created_at`, `updated_at`) VALUES
-- (1, 'user@user.com', 'user', 'user1234', '2024-10-29 12:24:09', '2024-10-29 12:24:09');
(1, 'user2@user.com', 'user2', '$2a$10$42JxNsFCSy1hDMxHNtIQ/.25PKqIqdSFI80lSYsVCRYXA1YeYnxcu', 'USER', '2024-10-29 12:24:09', '2024-10-29 12:24:09');
-- (2, 'user@user.com', 'user', '$2a$10$E3yySaODCjXD.9sQ7VyUWunKhfZHnuL6GCHcLTNqxvnyLx3r2HfFu', 'USER', '2024-11-27 12:24:09', '2024-11-27 12:24:09');

-- Déchargement des données de la table `rentals`
INSERT INTO `rentals` (`id`, `name`, `surface`, `price`, `picture`, `description`, `owner_id`, `created_at`, `updated_at`) VALUES
(1, 'Appartement Centre Ville', '55', '1200', 'appartement1.jpg', 'Appartement moderne au centre-ville.', 1, '2024-10-29 12:24:09', '2024-10-29 12:24:09'),
(2, 'Maison de campagne', '120', '1800', 'maison1.jpg', 'Grande maison en pleine nature.', 1, '2024-10-29 12:24:09', '2024-10-29 12:24:09'),
(3, 'Studio Quartier Latin', '30', '850', 'studio1.jpg', 'Petit studio dans le Quartier Latin, idéal pour étudiant.', 1, '2024-10-29 12:24:09', '2024-10-29 12:24:09');

-- Déchargement des données de la table `messages`
INSERT INTO `messages` (`id`, `rental_id`, `user_id`, `message`, `created_at`, `updated_at`) VALUES
(1, 1, 1, 'Je suis intéressé par cet appartement.', '2024-10-29 12:24:09', '2024-10-29 12:24:09'),
(2, 2, 1, 'Je souhaite visiter cette maison.', '2024-10-29 12:24:09', '2024-10-29 12:24:09'),
(3, 3, 1, 'Est-ce que ce studio est encore disponible ?', '2024-10-29 12:24:09', '2024-10-29 12:24:09');


